package space.pixelsg.urlgallery.db.provider

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import space.pixelsg.urlgallery.db.model.MediaStoreBucket
import space.pixelsg.urlgallery.db.model.MediaStoreItem
import java.time.Instant
import javax.inject.Inject

class GalleryProvider @Inject constructor(private val context: Context) {
    private val allImagesCursor by lazy {
        context.contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            arrayOf(
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
            ),
            null,
            null,
            MediaStore.Images.Media.DATE_TAKEN + " DESC",
        )
    }

    fun getAlbumsFlow() = flow {
        emit(getAlbums())
    }.flowOn(Dispatchers.IO)

    private suspend fun getAlbums(): List<MediaStoreBucket> {
        val items = mutableMapOf<Long, MediaStoreBucket>()
        val cursor = allImagesCursor ?: return emptyList()

        return withContext(Dispatchers.IO) {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)
            val bucketNameColumn =
                cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            cursor.moveToFirst()
            do {
                val imageID = cursor.getLong(idColumn)
                val bucketID = cursor.getLong(bucketIdColumn)
                val bucketName = cursor.getString(bucketNameColumn)
                val uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    imageID
                )
                if (items[bucketID] == null) items[bucketID] =
                    MediaStoreBucket(bucketID, bucketName, uri)
                else items[bucketID]!!.itemsCount++
            } while (cursor.moveToNext())
            return@withContext items.toList().map { it.second }
        }
    }

    suspend fun getImages(bucketID: Long, page: Int, limit: Int): List<MediaStoreItem> {
        val items = mutableListOf<MediaStoreItem>()
        var index = page * limit
        val cursor = getImagesCursor(bucketID) ?: return emptyList()

        return withContext(Dispatchers.IO) {
            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)
            val dateColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATE_TAKEN)
            val bucketIdColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)

            while (cursor.moveToPosition(index) && items.size < limit) {
                val id = cursor.getLong(idColumn)
                val imageBucketID = cursor.getLong(bucketIdColumn)
                if (imageBucketID == bucketID) items.add(
                    MediaStoreItem(
                        id,
                        cursor.getString(nameColumn),
                        ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                            id
                        ),
                        Instant.ofEpochMilli(cursor.getLong(dateColumn))
                    )
                )
                index++
            }
            return@withContext items
        }
    }

    private fun getImagesCursor(bucketID: Long) = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN,
        ),
        MediaStore.Images.Media.BUCKET_ID + "=?",
        arrayOf(bucketID.toString()),
        MediaStore.Images.Media.DATE_TAKEN + " DESC",
    )
}
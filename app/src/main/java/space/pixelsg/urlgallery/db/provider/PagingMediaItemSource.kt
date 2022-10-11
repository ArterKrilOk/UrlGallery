package space.pixelsg.urlgallery.db.provider

import androidx.paging.PagingSource
import androidx.paging.PagingState
import space.pixelsg.urlgallery.db.model.MediaStoreItem

class PagingMediaItemSource(
    private val galleryProvider: GalleryProvider,
    private val bucketID: Long
) : PagingSource<Int, MediaStoreItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MediaStoreItem> {
        return try {
            val page = params.key ?: 0
            val items = galleryProvider.getImages(bucketID, page, params.loadSize)

            LoadResult.Page(
                data = items,
                prevKey = if (page - 1 < 0) null else page - 1,
                nextKey = if (items.isEmpty()) null else page + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MediaStoreItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
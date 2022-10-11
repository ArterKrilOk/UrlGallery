package space.pixelsg.urlgallery.network.api

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.FileNotFoundException
import java.io.IOException

class UriRequestBody(
    private val uri: Uri,
    private val resolver: ContentResolver,
) : RequestBody() {

    override fun contentLength() =
        uri.length(resolver)

    override fun contentType() = null

    override fun writeTo(sink: BufferedSink) {
        val inputStream = resolver.openInputStream(uri)
            ?: throw IOException("Couldn't open content URI for reading")
        sink.write(inputStream.readBytes())
    }

    private fun Uri.length(contentResolver: ContentResolver): Long {

        val assetFileDescriptor = try {
            contentResolver.openAssetFileDescriptor(this, "r")
        } catch (e: FileNotFoundException) {
            null
        }

        val length = assetFileDescriptor?.use { it.length } ?: -1L
        if (length != -1L) {
            return length
        }

        if (scheme.equals(ContentResolver.SCHEME_CONTENT)) {
            return contentResolver.query(this, arrayOf(OpenableColumns.SIZE), null, null, null)
                ?.use { cursor ->
                    val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                    if (sizeIndex == -1) {
                        return@use -1L
                    }
                    cursor.moveToFirst()
                    return try {
                        cursor.getLong(sizeIndex)
                    } catch (_: Throwable) {
                        -1L
                    }
                } ?: -1L
        } else {
            return -1L
        }
    }
}
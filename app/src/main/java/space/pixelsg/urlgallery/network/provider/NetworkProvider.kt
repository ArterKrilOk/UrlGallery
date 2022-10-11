package space.pixelsg.urlgallery.network.provider

import android.content.ContentResolver
import android.net.Uri
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import space.pixelsg.urlgallery.network.api.Api
import space.pixelsg.urlgallery.network.api.UriRequestBody
import space.pixelsg.urlgallery.network.model.UploadResult
import javax.inject.Inject

class NetworkProvider @Inject constructor(
    private val api: Api
) {

    fun uploadFileFlow(uri: Uri, name: String, contentResolver: ContentResolver) = flow {
        emit(uploadFile(uri, name, contentResolver))
    }.flowOn(Dispatchers.IO)

    private suspend fun uploadFile(
        uri: Uri,
        name: String,
        contentResolver: ContentResolver
    ): List<UploadResult> {
        val body = MultipartBody.Part.createFormData(
            "file",
            name,
            UriRequestBody(uri, contentResolver)
        )
        return api.uploadFile(body)
    }
}
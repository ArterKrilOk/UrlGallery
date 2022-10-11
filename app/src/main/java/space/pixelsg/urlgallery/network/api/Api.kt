package space.pixelsg.urlgallery.network.api

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import space.pixelsg.urlgallery.network.model.UploadResult

interface Api {
    @Multipart
    @POST("upload/")
    suspend fun uploadFile(
        @Part filePart: MultipartBody.Part
    ): List<UploadResult>
}
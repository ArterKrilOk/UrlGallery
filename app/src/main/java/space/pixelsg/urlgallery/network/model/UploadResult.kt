package space.pixelsg.urlgallery.network.model

import com.google.gson.annotations.SerializedName

data class UploadResult(
    @SerializedName("name")
    val fileName: String,
    @SerializedName("file")
    val serverFileID: String,
    @SerializedName("key")
    val uploadKey: String
)
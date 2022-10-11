package space.pixelsg.urlgallery.ui.file.models

import android.net.Uri

data class SharableFile(
    val id: Long,
    val uri: Uri,
    val name: String,
    var link: String?
)
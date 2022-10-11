package space.pixelsg.urlgallery.db.model

import android.net.Uri

data class MediaStoreBucket(
    var id: Long,
    var name: String = "",
    var previewUri: Uri,
    var itemsCount: Int = 1,
)
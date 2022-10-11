package space.pixelsg.urlgallery.db.model

import android.net.Uri
import java.time.Instant

data class MediaStoreItem(
    val id: Long,
    val name: String,
    val uri: Uri,
    val date: Instant
)
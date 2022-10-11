package space.pixelsg.urlgallery.ui.albums.models

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil

data class AlbumItem(
    val id: Long,
    val name: String,
    val items: Int,
    val uri: Uri
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AlbumItem>() {
            override fun areItemsTheSame(oldItem: AlbumItem, newItem: AlbumItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: AlbumItem, newItem: AlbumItem) =
                oldItem == newItem
        }
    }
}

package space.pixelsg.urlgallery.ui.gallery.models

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import java.time.Instant

sealed class GalleryItem {
    data class Item(
        val id: Long,
        val uri: Uri,
        val name: String,
        val date: Instant,
    ) : GalleryItem()

    data class DateHeader(
        val instant: Instant
    ) : GalleryItem()

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GalleryItem>() {
            override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem) =
                if (oldItem is Item && newItem is Item) oldItem.id == newItem.id
                else oldItem == newItem

            override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem) =
                oldItem == newItem
        }
    }
}


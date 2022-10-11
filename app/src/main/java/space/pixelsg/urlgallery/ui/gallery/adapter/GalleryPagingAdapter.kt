package space.pixelsg.urlgallery.ui.gallery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import space.pixelsg.urlgallery.databinding.HeaderItemBinding
import space.pixelsg.urlgallery.databinding.ListItemBinding
import space.pixelsg.urlgallery.ui.gallery.models.GalleryItem

class GalleryPagingAdapter(
    private val onClick: (GalleryItem.Item) -> Unit
) : PagingDataAdapter<GalleryItem, RecyclerView.ViewHolder>(GalleryItem.DIFF_CALLBACK) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is GalleryItem.Item ->
                (holder as GalleryItemHolder).bind(item, onClick)
            is GalleryItem.DateHeader ->
                (holder as GalleryHeaderHolder).bind(item)
            else -> Unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE) GalleryItemHolder(
            ListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        ) else GalleryHeaderHolder(
            HeaderItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        is GalleryItem.Item -> ITEM_TYPE
        is GalleryItem.DateHeader -> HEADER_TYPE
        else -> -1
    }

    fun isHeader(position: Int) = getItem(position) is GalleryItem.DateHeader

    companion object {
        private const val ITEM_TYPE = 0
        private const val HEADER_TYPE = 1
    }
}
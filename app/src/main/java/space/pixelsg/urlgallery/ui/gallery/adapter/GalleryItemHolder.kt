package space.pixelsg.urlgallery.ui.gallery.adapter

import com.bumptech.glide.Glide
import space.pixelsg.urlgallery.common.adapter.BaseViewHolder
import space.pixelsg.urlgallery.databinding.ListItemBinding
import space.pixelsg.urlgallery.ui.gallery.models.GalleryItem

class GalleryItemHolder(
    binding: ListItemBinding
) : BaseViewHolder<GalleryItem.Item, ListItemBinding>(binding) {
    override fun bindItem(model: GalleryItem.Item) {
        Glide
            .with(binding.root)
            .load(model.uri)
            .override(300)
            .centerCrop()
            .into(binding.imageView)
    }
}
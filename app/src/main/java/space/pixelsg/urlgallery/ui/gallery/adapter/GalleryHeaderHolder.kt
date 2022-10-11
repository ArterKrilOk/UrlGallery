package space.pixelsg.urlgallery.ui.gallery.adapter

import space.pixelsg.urlgallery.common.adapter.BaseViewHolder
import space.pixelsg.urlgallery.databinding.HeaderItemBinding
import space.pixelsg.urlgallery.ui.UiUtils
import space.pixelsg.urlgallery.ui.gallery.models.GalleryItem
import java.time.ZoneId

class GalleryHeaderHolder(
    binding: HeaderItemBinding
) : BaseViewHolder<GalleryItem.DateHeader, HeaderItemBinding>(binding) {
    override fun bindItem(model: GalleryItem.DateHeader) {
        binding.textView.text =
            UiUtils.HeaderDateFormatter.format(model.instant.atZone(ZoneId.systemDefault()))
    }
}
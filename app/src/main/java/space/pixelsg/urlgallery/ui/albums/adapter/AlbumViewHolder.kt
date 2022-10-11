package space.pixelsg.urlgallery.ui.albums.adapter

import com.bumptech.glide.Glide
import space.pixelsg.urlgallery.common.adapter.BaseViewHolder
import space.pixelsg.urlgallery.databinding.AlbumItemBinding
import space.pixelsg.urlgallery.ui.albums.models.AlbumItem

class AlbumViewHolder(
    binding: AlbumItemBinding
) : BaseViewHolder<AlbumItem, AlbumItemBinding>(binding) {
    override fun bindItem(model: AlbumItem) {
        binding.textView.text = model.name
        Glide
            .with(binding.root)
            .load(model.uri)
            .override(600)
            .centerCrop()
            .into(binding.imageView)
    }
}
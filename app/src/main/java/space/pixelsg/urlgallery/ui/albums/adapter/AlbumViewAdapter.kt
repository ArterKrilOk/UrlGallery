package space.pixelsg.urlgallery.ui.albums.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import space.pixelsg.urlgallery.common.adapter.BaseAdapter
import space.pixelsg.urlgallery.databinding.AlbumItemBinding
import space.pixelsg.urlgallery.ui.albums.models.AlbumItem

class AlbumViewAdapter(
    onClick: (AlbumItem) -> Unit
) : BaseAdapter<AlbumItem, AlbumViewHolder>(AlbumItem.DIFF_CALLBACK, onClick) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AlbumViewHolder(
        AlbumItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )
}
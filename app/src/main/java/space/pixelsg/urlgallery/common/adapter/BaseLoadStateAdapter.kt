package space.pixelsg.urlgallery.common.adapter

import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

abstract class BaseLoadStateAdapter<VH : BaseLoadStateViewHolder<*>> : LoadStateAdapter<VH>() {
    override fun onBindViewHolder(holder: VH, loadState: LoadState) =
        holder.bind(loadState)
}
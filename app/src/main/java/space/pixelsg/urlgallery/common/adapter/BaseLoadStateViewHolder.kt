package space.pixelsg.urlgallery.common.adapter

import androidx.paging.LoadState
import androidx.viewbinding.ViewBinding

abstract class BaseLoadStateViewHolder<B : ViewBinding>(binding: B) :
    BaseViewHolder<LoadState, B>(binding)
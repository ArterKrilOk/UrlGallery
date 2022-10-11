package space.pixelsg.urlgallery.ui.albums

import kotlinx.coroutines.flow.map
import space.pixelsg.urlgallery.App
import space.pixelsg.urlgallery.common.mvvm.BaseViewModel
import space.pixelsg.urlgallery.ui.albums.models.AlbumItem

class AlbumsViewModel(app: App) : BaseViewModel(app) {
    private val galleryProvider = graph.galleryProvider

    val albums = galleryProvider.getAlbumsFlow().map { list ->
        list.map {
            AlbumItem(
                it.id,
                it.name,
                it.itemsCount,
                it.previewUri
            )
        }
    }.shareWhileSubscribed()
}
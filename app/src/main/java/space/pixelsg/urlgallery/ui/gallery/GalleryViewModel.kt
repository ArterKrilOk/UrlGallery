package space.pixelsg.urlgallery.ui.gallery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.map
import space.pixelsg.urlgallery.App
import space.pixelsg.urlgallery.common.mvvm.BaseViewModel
import space.pixelsg.urlgallery.db.provider.PagingMediaItemSource
import space.pixelsg.urlgallery.ui.gallery.models.GalleryItem
import java.time.ZoneId

class GalleryViewModel(app: App, bucketID: Long) : BaseViewModel(app) {
    val pagedGallery = Pager(
        PagingConfig(
            pageSize = 40,
            initialLoadSize = 40,
            enablePlaceholders = false
        )
    ) {
        PagingMediaItemSource(graph.galleryProvider, bucketID)
    }.flow.cachedIn(viewModelScope).map { pagingData ->
        pagingData.map {
            GalleryItem.Item(
                it.id,
                it.uri,
                it.name,
                it.date,
            )
        }.insertSeparators { i1, i2 ->
            when {
                i1 == null && i2 != null -> GalleryItem.DateHeader(i2.date)
                i1 != null && i2 != null -> {
                    val d1 = i1.date.atZone(ZoneId.systemDefault()).toLocalDate()
                    val d2 = i2.date.atZone(ZoneId.systemDefault()).toLocalDate()
                    //Add separator then days are different
                    if (d1 != d2) GalleryItem.DateHeader(i2.date)
                    else null
                }
                else -> null
            }
        }
    }.shareWhileSubscribed()


    class Factory(private val app: App, private val bucketID: Long) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass
                .getConstructor(App::class.java, Long::class.java)
                .newInstance(app, bucketID)
        }
    }
}
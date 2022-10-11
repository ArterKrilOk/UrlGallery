package space.pixelsg.urlgallery.di

import dagger.Component
import space.pixelsg.urlgallery.db.di.DatabaseModule
import space.pixelsg.urlgallery.db.provider.GalleryProvider
import space.pixelsg.urlgallery.db.provider.SharedLinksProvider
import space.pixelsg.urlgallery.network.di.NetworkModule
import space.pixelsg.urlgallery.network.provider.NetworkProvider

@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
    ]
)
@AppScope
interface DepGraph {
    val galleryProvider: GalleryProvider
    val networkProvider: NetworkProvider
    val sharedLinksProvider: SharedLinksProvider
}
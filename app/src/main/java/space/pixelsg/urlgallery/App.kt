package space.pixelsg.urlgallery

import android.app.Application
import com.google.android.material.color.DynamicColors
import space.pixelsg.urlgallery.db.di.DatabaseModule
import space.pixelsg.urlgallery.di.AppModule
import space.pixelsg.urlgallery.di.DaggerDepGraph
import space.pixelsg.urlgallery.network.di.NetworkModule

class App : Application() {
    val graph by lazy {
        DaggerDepGraph.builder()
            .appModule(AppModule(this))
            .databaseModule(DatabaseModule())
            .networkModule(NetworkModule())
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }
}
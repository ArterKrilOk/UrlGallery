package space.pixelsg.urlgallery.db.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import space.pixelsg.urlgallery.db.Database
import space.pixelsg.urlgallery.di.AppScope

@Module
class DatabaseModule {
    @Provides
    @AppScope
    fun provideDatabase(context: Context) = Room.databaseBuilder(
        context,
        Database::class.java,
        "database"
    ).build()

    @Provides
    @AppScope
    fun provideSharedLinksDao(database: Database) = database.getSharedLinkDao()
}
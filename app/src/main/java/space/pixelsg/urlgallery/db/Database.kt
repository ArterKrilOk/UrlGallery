package space.pixelsg.urlgallery.db

import androidx.room.Database
import androidx.room.RoomDatabase
import space.pixelsg.urlgallery.db.dao.SharedLinkDao
import space.pixelsg.urlgallery.db.model.SharedLink

@Database(
    version = 1,
    entities = [
        SharedLink::class
    ]
)
abstract class Database : RoomDatabase() {
    abstract fun getSharedLinkDao(): SharedLinkDao
}
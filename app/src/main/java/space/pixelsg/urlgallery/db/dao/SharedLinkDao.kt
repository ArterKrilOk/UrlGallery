package space.pixelsg.urlgallery.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import space.pixelsg.urlgallery.db.model.SharedLink

@Dao
interface SharedLinkDao {
    @Insert
    fun insert(sharedLink: SharedLink): Long

    @Query("SELECT * FROM sharedlink WHERE id=:id")
    fun getById(id: Long): Flow<SharedLink?>
}
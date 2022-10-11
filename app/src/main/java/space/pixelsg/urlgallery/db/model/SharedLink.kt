package space.pixelsg.urlgallery.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SharedLink(
    @PrimaryKey
    val id: Long,
    @ColumnInfo(name = "link")
    val link: String
)
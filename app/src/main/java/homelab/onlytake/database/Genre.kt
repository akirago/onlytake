package homelab.onlytake.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class Genre(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)
package homelab.onlytake.database

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "cloth",
    foreignKeys = [ForeignKey(
        entity = Genre::class,
        parentColumns = ["id"],
        childColumns = ["genre_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Cloth(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val display: String,
    val type: String,
    val used_count: Int,
    val genre_id: Int,
    val picturePath: String // Change from BLOB to String
)
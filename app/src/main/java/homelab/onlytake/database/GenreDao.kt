package homelab.onlytake.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GenreDao {
    @Insert
    suspend fun insert(genre: Genre)

    @Query("SELECT * FROM genre")
    fun getAllGenres(): List<Genre>

    @Delete
    suspend fun delete(genre: Genre)
}
package homelab.onlytake.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {
    @Insert
    suspend fun insert(genre: Genre)

    @Query("SELECT * FROM genre")
    fun getAllGenres(): Flow<List<Genre>>

    @Delete
    suspend fun delete(genre: Genre)
}
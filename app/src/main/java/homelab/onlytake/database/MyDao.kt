package homelab.onlytake.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query

@Dao
interface MyDao {
    @Query("SELECT * FROM genre")
    suspend fun getAllGenres(): List<Genre>

    @Query("SELECT * FROM cloth WHERE genre_id = :genreId")
    suspend fun getClothesByGenre(genreId: Int): List<Cloth>

    @Query("DELETE FROM cloth WHERE id = :id")
    suspend fun deleteClothById(id: Int)
}

package homelab.onlytake.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ClothDao {
    @Insert
    suspend fun insert(cloth: Cloth)

//    @Query("SELECT * FROM cloth WHERE genre_id = :genre")
//    fun getClothes(genre: Genre): List<Cloth>
}
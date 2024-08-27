package homelab.onlytake.cloth

import homelab.onlytake.database.ClothDao
import homelab.onlytake.database.Genre

class ClothRepository(private val clothDao: ClothDao) {

    suspend fun getClothes(genre: Genre) {
//        clothDao.getClothes(genre)
    }
}
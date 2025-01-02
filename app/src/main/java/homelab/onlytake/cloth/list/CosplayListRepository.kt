package homelab.onlytake.cloth.list

import homelab.onlytake.database.Cloth
import homelab.onlytake.database.ClothDao
import homelab.onlytake.database.GenreDao
import kotlinx.coroutines.flow.Flow

class CosplayListRepository(private val clothDao: ClothDao, private val genreDao: GenreDao) {

    val allGenres = genreDao.getAllGenres()

//    val allClothes: Flow<List<List<Cloth>>> by lazy {
//
//    }
}
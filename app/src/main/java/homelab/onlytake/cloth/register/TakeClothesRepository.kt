package homelab.onlytake.cloth.register

import homelab.onlytake.database.Genre
import homelab.onlytake.database.GenreDao
import kotlinx.coroutines.flow.Flow

class TakeClothesRepository(private val genreDao: GenreDao) {

    val allGenre: Flow<List<Genre>> = genreDao.getAllGenres()

}
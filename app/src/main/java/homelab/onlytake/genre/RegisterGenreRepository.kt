package homelab.onlytake.genre

import androidx.annotation.WorkerThread
import homelab.onlytake.database.Genre
import homelab.onlytake.database.GenreDao
import kotlinx.coroutines.flow.Flow

class RegisterGenreRepository(private val genreDao: GenreDao) {

    val allGenre: Flow<List<Genre>> = genreDao.getAllGenres()

    @WorkerThread
    suspend fun insert(genre: Genre) {
        genreDao.insert(genre)
    }

    suspend fun delete(genre: Genre) {
        genreDao.delete(genre)
    }
}
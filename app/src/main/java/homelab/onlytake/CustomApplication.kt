package homelab.onlytake

import android.app.Application
import android.content.Context
import homelab.onlytake.database.AppDatabase
import homelab.onlytake.genre.RegisterGenreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class CustomApplication : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        context = applicationContext
        super.onCreate()
    }

    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(context, applicationScope) }
    val registerGenreRepository by lazy { RegisterGenreRepository(database.genreDao()) }
    val clothRepository by lazy { RegisterGenreRepository(database.genreDao()) }
}

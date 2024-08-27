package homelab.onlytake.genre

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import homelab.onlytake.database.Genre
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class RegisterGenreViewModel(private val repository: RegisterGenreRepository) : ViewModel() {

    private val _allGenre: StateFlow<List<Genre>> = repository.allGenre.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )
    val allGenre: StateFlow<List<Genre>> get() = _allGenre

    suspend fun addGenre(genre: Genre) {
        repository.insert(genre)
    }

    suspend fun deleteGenre(genre: Genre) {
        repository.delete(genre)
    }
}

@Suppress("UNCHECKED_CAST")
class RegisterGenreViewModelFactory(private val repository: RegisterGenreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegisterGenreViewModel::class.java)) {
            return RegisterGenreViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
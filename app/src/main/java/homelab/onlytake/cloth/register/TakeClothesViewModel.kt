package homelab.onlytake.cloth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import homelab.onlytake.database.Genre
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class TakeClothesViewModel(private val repository: TakeClothesRepository): ViewModel() {

    private val _allGenre: StateFlow<List<Genre>> = repository.allGenre.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList()
    )
    val allGenre: StateFlow<List<Genre>> get() = _allGenre

}


@Suppress("UNCHECKED_CAST")
class TakeClothesViewModelFactory(private val repository: TakeClothesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TakeClothesViewModel::class.java)) {
            return TakeClothesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
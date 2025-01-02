package homelab.onlytake.cloth.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import homelab.onlytake.database.Cloth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CosplayListViewModel(private val repository: CosplayListRepository): ViewModel() {

    fun fetchHeaderListData(onResult: (List<HeaderListData>) -> Unit) {
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO) { repository.getHeaderListData() }
            onResult(data)
        }
    }

    fun deleteClothData(id: Int) {
        viewModelScope.launch {
            repository.deleteCloth(id)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class CosplayListViewModelFactory(private val repository: CosplayListRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CosplayListViewModel::class.java)) {
            return CosplayListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
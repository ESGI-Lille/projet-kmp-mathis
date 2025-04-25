// File: HomeViewModel.kt
package org.example.project.ViewModel

import ApiReponsePerson
import ApiResponseObject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.Network.getDataFromRandomAuthor
import org.example.project.Network.getRandomAuthor

class HomeViewModel : ViewModel() {

    private val _authorData = MutableStateFlow<ApiReponsePerson?>(null)
    val authorData: StateFlow<ApiReponsePerson?> = _authorData.asStateFlow()

    private val _collectionsData = MutableStateFlow<ApiResponseObject?>(null)
    val data: StateFlow<ApiResponseObject?> = _collectionsData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getRandomAuthorAndItsCollections() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val authorData = getRandomAuthor()
                val collectionsData = getDataFromRandomAuthor()
                _collectionsData.value = collectionsData
                _authorData.value = authorData
                _isLoading.value = false
                _error.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _collectionsData.value = null
                _authorData.value = null
                _isLoading.value = false
                _error.value = "Erreur lors de la récupération des données : ${e.message}"
            }
        }
    }

    // Plus besoin de returnData() de cette manière pour l'UI Compose
}
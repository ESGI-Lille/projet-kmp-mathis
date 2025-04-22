// File: HomeViewModel.kt
package org.example.project.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.Network.getData

class HomeViewModel : ViewModel() {

    private val _data = MutableStateFlow<String?>(null)
    val data: StateFlow<String?> = _data.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun fetchData() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val fetchedData = getData()
                _data.value = fetchedData
                _isLoading.value = false
                _error.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _data.value = null
                _isLoading.value = false
                _error.value = "Erreur lors de la récupération des données : ${e.message}"
            }
        }
    }

    // Plus besoin de returnData() de cette manière pour l'UI Compose
}
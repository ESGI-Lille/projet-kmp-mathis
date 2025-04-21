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

    fun fetchData() {
        viewModelScope.launch {
            try {
                val fetchedData = getData()
                _data.value = fetchedData
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
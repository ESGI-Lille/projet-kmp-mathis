package org.example.project.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.Network.searchMovies
import org.example.project.Network.Entity.Movie

/**
 * ViewModel pour la recherche de films
 */
class SearchViewModel : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _results = MutableStateFlow<List<Movie>>(emptyList())
    val results: StateFlow<List<Movie>> = _results.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Met à jour la requête de recherche et déclenche la recherche
    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }

    // Lance la recherche de films pour la query courante
    fun search() {
        val q = _query.value.trim()
        if (q.isEmpty()) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = searchMovies(q)
                val list = response.results ?: emptyList()
                _results.value = list
                _error.value = null
            } catch (e: Exception) {
                e.printStackTrace()
                _results.value = emptyList()
                _error.value = "Erreur lors de la recherche : ${'$'}{e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

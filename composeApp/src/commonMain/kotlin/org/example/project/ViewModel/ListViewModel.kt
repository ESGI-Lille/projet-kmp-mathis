package org.example.project.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.Network.getWatchlistLocal
import org.example.project.Network.getFavoritesLocal
import org.example.project.Network.Entity.MovieDetail

/**
 * ViewModel pour récupérer, exposer et modifier les listes Watchlist et Favoris
 */
class ListsViewModel : ViewModel() {

    private val _watchlist = MutableStateFlow<List<MovieDetail>>(emptyList())
    val watchlist: StateFlow<List<MovieDetail>> = _watchlist.asStateFlow()

    private val _favorites = MutableStateFlow<List<MovieDetail>>(emptyList())
    val favorites: StateFlow<List<MovieDetail>> = _favorites.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /** Charge la watchlist */
    fun loadWatchlist() = loadList(type = "watchlist")

    /** Charge les favoris */
    fun loadFavorites() = loadList(type = "favorites")

    private fun loadList(type: String) {
        _isLoading.value = true
        _error.value = null
        viewModelScope.launch {
            try {
                if (type == "watchlist") {
                    _watchlist.value = getWatchlistLocal()
                    _favorites.value = emptyList()
                } else {
                    _favorites.value = getFavoritesLocal()
                    _watchlist.value = emptyList()
                }
            } catch (e: Exception) {
                _error.value = "Erreur récupération $type: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

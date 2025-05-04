package org.example.project.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.Network.getMovieData
import org.example.project.Network.Entity.MovieDetail
import org.example.project.Network.addToFavoritesLocal
import org.example.project.Network.addToWatchlistLocal

/**
 * ViewModel pour l'écran de détail d'un film, utilise JSON Server local pour watchlist/favoris
 */
class DetailPageViewModel : ViewModel() {

    private val _movie = MutableStateFlow<MovieDetail?>(null)
    val movie: StateFlow<MovieDetail?> = _movie.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    /**
     * Charge les détails du film
     */
    suspend fun getSingleMovieData(movieId: Int) {
        _isLoading.value = true
        _error.value = null
        try {
            val movieDetail = getMovieData(movieId)
            _movie.value = movieDetail
        } catch (e: Exception) {
            _error.value = "Erreur chargement détails: ${e.localizedMessage}"
            _movie.value = null
        } finally {
            _isLoading.value = false
        }
    }

    /**
     * Ajoute un film à la watchlist locale
     */
    fun addToWatchlist(movieData: MovieDetail) {
        viewModelScope.launch {
            val success = addToWatchlistLocal(movieData)
            if (!success) _error.value = "Impossible d'ajouter à la watchlist locale"
        }
    }

    /**
     * Ajoute un film aux favoris locaux
     */
    fun addToFavorites(movieId: MovieDetail) {
        viewModelScope.launch {
            val success = addToFavoritesLocal(movieId)
            if (!success) _error.value = "Impossible d'ajouter aux favoris locaux"
        }
    }
}
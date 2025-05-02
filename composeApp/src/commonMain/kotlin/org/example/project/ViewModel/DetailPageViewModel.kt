package org.example.project.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.Network.Entity.Movie
import org.example.project.Network.Entity.MovieDetail

import org.example.project.Network.getMovieData


class DetailPageViewModel: ViewModel() {

    // Dans votre DetailPageViewModel
    private val _movie = MutableStateFlow<MovieDetail?>(null)
    val movie: StateFlow<MovieDetail?> = _movie.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun getSingleMovieData(movieId: Int) {
        _isLoading.value = true
        _error.value = null // Réinitialiser l'erreur au début de la requête
        try {
            val movieDetail = getMovieData(movieId) // Supposons que vous ayez un repository
            _movie.value = movieDetail
            _isLoading.value = false
        } catch (e: Exception) {
            _error.value = "Erreur lors du chargement des détails du film: ${e.localizedMessage}"
            _isLoading.value = false
            _movie.value = null // Réinitialiser les données en cas d'erreur
        }
    }
}


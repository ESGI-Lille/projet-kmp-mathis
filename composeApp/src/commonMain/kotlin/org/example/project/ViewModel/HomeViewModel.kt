package org.example.project.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.Network.Entity.Movie
import org.example.project.Network.getRandomMovies

class HomeViewModel() : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()


    fun loadRandomMovies() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                println("Début de la requête pour obtenir des films aléatoires")
                val response = getRandomMovies()
                println("Réponse reçue, nombre de résultats: ${response.results?.size ?: 0}")

                if (response.results != null && response.results.isNotEmpty()) {
                    _movies.value = response.results
                    println("Films mis à jour: ${_movies.value.size} films")
                } else {
                    println("La liste de résultats est vide ou nulle")
                    _movies.value = emptyList()
                }

                _isLoading.value = false
                _error.value = null
            } catch (e: Exception) {
                println("Exception: ${e.message}")
                e.printStackTrace()
                _movies.value = emptyList()
                _isLoading.value = false
                _error.value = "Erreur lors de la récupération des films : ${e.message}"
            }
        }
    }
}
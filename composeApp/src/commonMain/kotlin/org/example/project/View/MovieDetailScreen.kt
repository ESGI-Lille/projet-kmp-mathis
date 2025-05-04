package org.example.project.View

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch
import org.example.project.Network.Entity.MovieDetail
import org.example.project.ViewModel.DetailPageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(navController: NavController, movieId: Int?, detailPageViewModel: DetailPageViewModel) {

    // Données du view model
    val movieData by detailPageViewModel.movie.collectAsState()
    val isLoading by detailPageViewModel.isLoading.collectAsState()
    val error by detailPageViewModel.error.collectAsState()

    // Pour les snackbars
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Reload de la page a chaque changement d'ID
    LaunchedEffect(movieId) {
        movieId?.let { detailPageViewModel.getSingleMovieData(it) }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                },
                title = { Text("Search Results") }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // isloading true
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                error != null -> Text(
                    text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                // Affichage OK
                movieData != null -> {
                    val movie = movieData!!
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(text = movie.title.orEmpty(), style = MaterialTheme.typography.headlineLarge)
                        movie.tagline?.let { Text(text = "\"$it\"", style = MaterialTheme.typography.titleMedium) }
                        AsyncImage(
                            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                            contentDescription = movie.title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )
                        Text(text = "Sortie: ${movie.releaseDate.orEmpty()}")
                        Text(text = "Score: ${movie.voteAverage ?: 0.0}")
                        movie.overview?.let { Text(text = it) }
                        Spacer(Modifier.height(16.dp))

                        // Boutons d'actions
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Button(
                                onClick = {
                                    detailPageViewModel.addToWatchlist(movie)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Film ajouté à la watchlist")
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Watchlist") }
                            Button(
                                onClick = {
                                    detailPageViewModel.addToFavorites(movie)
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Film ajouté aux favoris")
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text("Favoris") }
                        }
                    }
                }

                // loading KO
                else -> Text(
                    text = "Aucun détail disponible",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

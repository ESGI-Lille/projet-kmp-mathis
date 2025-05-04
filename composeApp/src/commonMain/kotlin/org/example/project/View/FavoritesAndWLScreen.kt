package org.example.project.View

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import org.example.project.ViewModel.ListsViewModel
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesMoviesListScreen(navController: NavController, viewModel: ListsViewModel, type: String) {
    // Données du view model
    val watchlist by viewModel.watchlist.collectAsState()
    val favorites by viewModel.favorites.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    //Snackbar
    val snackbarHostState = remember { SnackbarHostState() }


    // Différentes requêtes selon la valeur de type
    LaunchedEffect(type) {
        if (type == "watchlist") {
            viewModel.loadWatchlist()
        } else {
            viewModel.loadFavorites()
        }
    }

    // movies change selon le type également
    val movies = if (type == "watchlist") {
        watchlist
    } else {
        favorites
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                title = {
                    Text(text = if (type == "watchlist") "Ma Watchlist" else "Mes Favoris", style = MaterialTheme.typography.headlineSmall)
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Box(
            Modifier.fillMaxSize().padding(paddingValues)
        ) {
            when {
                // Isloading = true
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                error != null -> Text(text = error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center)
                )
                //  Movies == empty
                movies.isEmpty() -> Text(
                    text = "Aucun film ${if (type == "watchlist") "en watchlist" else "en favoris"}.",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
                // Affichage OK
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(movies) { movie ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.LightGray.copy(alpha = 0.2f))
                                .clickable { navController.navigate("detail/${movie.id}") }
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w200${movie.posterPath}",
                                contentDescription = movie.title,
                                modifier = Modifier.size(80.dp).clip(MaterialTheme.shapes.medium)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(movie.title.orEmpty(), style = MaterialTheme.typography.titleMedium)
                                Text(movie.releaseDate.orEmpty(), style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
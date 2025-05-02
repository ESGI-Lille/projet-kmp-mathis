import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import org.example.project.Network.Entity.Genre
import org.example.project.Network.Entity.MovieDetail
import org.example.project.Network.Entity.ProductionCompany
import org.example.project.ViewModel.DetailPageViewModel


@Composable
fun MovieDetailScreen(navController: NavController, movieId: Int?, detailPageViewModel: DetailPageViewModel = viewModel()) {
    val movieData by detailPageViewModel.movie.collectAsState()
    val isLoading by detailPageViewModel.isLoading.collectAsState()
    val error by detailPageViewModel.error.collectAsState()

    LaunchedEffect(movieId) {
        if (movieId != null) {
            detailPageViewModel.getSingleMovieData(movieId)
        }
    }

    if (isLoading) {
        Text("Chargement des détails du film...")
    } else if (error != null) {
        Text("Erreur: $error")
    } else if (movieData != null) {
        // Affiche le composable de détails en passant movieData
        MovieDetailContent(movie = movieData!!)
    } else {
        Text("Aucun détail de film trouvé.")
    }
}

@Composable
fun MovieDetailContent(movie: MovieDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Affiche le titre du film
        Text(
            text = movie.title ?: "Titre inconnu",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        // Affiche la tagline du film, si elle existe
        if (movie.tagline != null) {
            Text(
                text = "\"${movie.tagline}\"",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Affiche l'image de l'affiche du film
            if (movie.posterPath != null) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                    contentDescription = "Affiche du film ${movie.title}",
                    modifier = Modifier
                        .width(150.dp)
                        .aspectRatio(0.67f) // Ratio d'aspect typique d'une affiche de film
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Crop
                )
            }

            Column {
                // Affiche la date de sortie
                if (movie.releaseDate != null) {
                    Text(text = "Sortie: ${movie.releaseDate}", style = MaterialTheme.typography.bodyMedium)
                }

                // Affiche le score moyen et le nombre de votes
                if (movie.voteAverage != null && movie.voteCount != null) {
                    Text(
                        text = "Score: ${movie.voteAverage} (${movie.voteCount} votes)",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        // Affiche la liste des genres
        if (movie.genres != null && movie.genres.isNotEmpty()) {
            Text(text = "Genres:", style = MaterialTheme.typography.titleMedium)
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(movie.genres) { genre ->
                    GenreChip(genre = genre)
                }
            }
        }

        // Affiche la description du film
        if (movie.overview != null) {
            Text(text = "Synopsis:", style = MaterialTheme.typography.titleMedium)
            Text(text = movie.overview, style = MaterialTheme.typography.bodyLarge)
        }

        // Affiche la liste des compagnies de production
        if (movie.productionCompanies.isNotEmpty()) {
            Text(text = "Production:", style = MaterialTheme.typography.titleMedium)
            Column {
                movie.productionCompanies.forEach { company ->
                    ProductionCompanyItem(company = company)
                }
            }
        }
    }
}

@Composable
fun GenreChip(genre: Genre) {
    SuggestionChip(onClick = {}, label = { Text(genre.name ?: "Inconnu") })
}

@Composable
fun ProductionCompanyItem(company: ProductionCompany) {
    Text(text = company.name)
    // Vous pourriez ajouter le logo de la compagnie ici si logoPath n'est pas null
}
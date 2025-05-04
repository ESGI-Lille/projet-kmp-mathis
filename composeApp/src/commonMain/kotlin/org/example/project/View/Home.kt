package org.example.project.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import org.example.project.ViewModel.SearchViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import art_kotlin.composeapp.generated.resources.Res
import art_kotlin.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import javax.swing.Painter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, viewModel: SearchViewModel) {

    // Données du view model
    val query by viewModel.query.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Accueil") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Section supérieure : texte et recherche
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Bienvenue dans votre application de gestion de films !",
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24.sp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Découvrez, suivez et gérez vos films préférés.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                // Input recherche
                OutlinedTextField(
                    value = query,
                    onValueChange = { viewModel.setQuery(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    placeholder = { Text("Rechercher un film...") },
                    singleLine = true,
                    trailingIcon = {
                        IconButton(onClick = {
                            viewModel.search()
                            if (query.isNotBlank()) {
                                navController.navigate("searchResults/$query")
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Rechercher"
                            )
                        }
                    }
                )

                // Isloading true
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 8.dp))
                }
                // error true
                error?.let {
                    Text(
                        text = it,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                // Boutons pour Watchlist et Favoris
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = { navController.navigate("watchlist") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.DarkGray
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Watchlist"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Watchlist")
                    }
                    Button(
                        onClick = { navController.navigate("favorites") },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Magenta
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoris"
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Favoris")
                    }
                }
            }

            // Bouton Découvrir en bas
            Button(
                onClick = { navController.navigate("RandomMovies") },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Découvrir des films aleatoires",
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Watchlist"
                )
            }
        }
    }
}

package org.example.project.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import art_kotlin.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import art_kotlin.composeapp.generated.resources.logo
import org.example.project.ViewModel.HomeViewModel

@Composable
fun CardTableau(imageResource: DrawableResource) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp), // Ajoute du padding à l'intérieur de la carte
            horizontalAlignment = Alignment.CenterHorizontally // Centre les éléments horizontalement
        ) {
            Text(
                text = "Leonard de Vinci",
                style = MaterialTheme.typography.headlineSmall, // Utilise un style de titre
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp) // Ajoute un peu d'espace sous le nom
            )
            Image(
                painter = painterResource(imageResource),
                contentDescription = null,
                modifier = Modifier
                    .size(300.dp) // Réduit légèrement la taille de l'image
                    .padding(bottom = 8.dp) // Ajoute un peu d'espace sous l'image
            )
            Row(
                modifier = Modifier.fillMaxWidth(), // Fait en sorte que la Row prenne toute la largeur
                horizontalArrangement = Arrangement.SpaceBetween, // Répartit l'espace entre les textes
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "1872",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold), // Style pour l'année
                )
                Text(
                    text = "affiché au musée d'Orsay",
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic), // Style pour le lieu
                )
            }
        }
    }
}


@Composable
fun Container(homeViewModel: HomeViewModel = viewModel()) {
    val data: String? by homeViewModel.data.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val error by homeViewModel.error.collectAsState()

    if (data.isNullOrEmpty() && !isLoading && error.isNullOrEmpty()) {
        LaunchedEffect(Unit) {
            homeViewModel.fetchData()
        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (error != null) {
                Text(text = "Erreur : $error", color = MaterialTheme.colorScheme.error)
            } else if (!data.isNullOrEmpty()) {
                Text(text = "Données récupérées : $data")
            } else {
                Text(text = "Aucune donnée disponible.")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        val itemsList = listOf(
            Res.drawable.logo,
            Res.drawable.logo,
            Res.drawable.logo,
            Res.drawable.logo
        )
        items(itemsList) { item ->
            CardTableau(imageResource = item)
        }
    }
}
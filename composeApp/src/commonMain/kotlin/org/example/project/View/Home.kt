// File: Home.kt
package org.example.project.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import art_kotlin.composeapp.generated.resources.Res
import art_kotlin.composeapp.generated.resources.compose_multiplatform
import art_kotlin.composeapp.generated.resources.téléchargement
import org.example.project.ViewModel.HomeViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun Home(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    val dataFromApi by homeViewModel.data.collectAsState()
    val isLoading = dataFromApi == null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {

        // Les deux titres principaux

        Text(
            text = "Harvard Museum",
            style = MaterialTheme.typography.headlineLarge.copy(
                fontWeight = FontWeight.Bold,
            ),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Explorez nos collections fascinantes d'art, de science et de culture.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF5E4A3D),
                fontSize = 18.sp
            ),
            textAlign = TextAlign.Center
        )

        // Boutons de navigation principaux
        Button(
            onClick = { navController.navigate("exposition") },
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB8860B)) // Couleur or
        ) {
            Text("Découvrez les Expositions", color = Color.White)
        }

        Button(
            onClick = { homeViewModel.fetchData() },
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B4513)) // Couleur bronze
        ) {
            Text("Charger les Données", color = Color.White)
        }
        Image(
            painter = painterResource(Res.drawable.compose_multiplatform),
            contentDescription = null
        )

    }
}
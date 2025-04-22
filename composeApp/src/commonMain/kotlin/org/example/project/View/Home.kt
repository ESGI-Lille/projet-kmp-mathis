// Home.kt
package org.example.project.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import art_kotlin.composeapp.generated.resources.Res
import art_kotlin.composeapp.generated.resources.logo
import org.example.project.ViewModel.HomeViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun Home(navController: NavController, homeViewModel: HomeViewModel = viewModel()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Explorez nos collections fascinantes d'art, de science et de culture.",
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color(0xFF5E4A3D),
                fontSize = 18.sp
            ),
            textAlign = TextAlign.Center
        )

        Image(
            painter = painterResource(resource = Res.drawable.logo),
            contentDescription = null
        )

        Button(
            onClick = {
                navController.navigate("exposition") // Navigatez simplement vers la route "exposition"
            },
            modifier = Modifier.fillMaxWidth(0.7f),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B4513))
        ) {
            Text("bouton2", color = Color.White)
        }
    }
}


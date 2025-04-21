package org.example.project.View


import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import art_kotlin.composeapp.generated.resources.Res
import art_kotlin.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.ui.tooling.preview.Preview



@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") { Home(navController) }
        composable("exposition") { Container(Res.drawable.compose_multiplatform) }
    }
}











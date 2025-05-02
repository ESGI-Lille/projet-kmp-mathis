package org.example.project.View

import MovieDetailScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.project.ViewModel.HomeViewModel

@Composable
fun App() {
    val navController = rememberNavController()

    Scaffold(
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            composable("home") { Home(navController) }
            composable("RandomMovies") { backStackEntry ->
                // on crée le VM ici, lié au backStackEntry
                val homeViewModel: HomeViewModel = viewModel(backStackEntry)
                MovieListScreen(
                    homeViewModel = homeViewModel,
                    navController = navController
                ) }
            composable(
                route = "detail/{movieId}",
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId")
                MovieDetailScreen(navController = navController, movieId = movieId) // Utilisation correcte ici
            }
        }
    }
}


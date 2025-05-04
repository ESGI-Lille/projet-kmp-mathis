package org.example.project.View

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.example.project.ViewModel.DetailPageViewModel
import org.example.project.ViewModel.HomeViewModel
import org.example.project.ViewModel.ListsViewModel
import org.example.project.ViewModel.SearchViewModel


@Composable
fun App(homeViewModel: HomeViewModel, searchViewModel: SearchViewModel, listsViewModel: ListsViewModel, detailPageViewModel: DetailPageViewModel) {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            composable("home") {
                Home(navController = navController, viewModel = searchViewModel)
            }
            composable("RandomMovies") {
                MovieListScreen(homeViewModel = homeViewModel, navController = navController)
            }
            composable("searchResults/{query}", arguments = listOf(navArgument("query") { type = NavType.StringType })
            ) { backStack ->
                val q = backStack.arguments?.getString("query") ?: ""
                LaunchedEffect(q) {
                    searchViewModel.setQuery(q)
                    searchViewModel.search()
                }
                val results by searchViewModel.results.collectAsState()
                SearchResultScreen(navController = navController, movies = results)
            }
            composable("detail/{movieId}", arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStack ->
                val id = backStack.arguments!!.getInt("movieId")
                MovieDetailScreen(navController = navController, movieId = id, detailPageViewModel = detailPageViewModel)
            }
            composable("watchlist") {
                FavoritesMoviesListScreen(navController = navController, viewModel = listsViewModel, type = "watchlist")
            }
            composable("favorites") {
                FavoritesMoviesListScreen(navController = navController, viewModel = listsViewModel, type = "favorites")
            }
        }
    }
}

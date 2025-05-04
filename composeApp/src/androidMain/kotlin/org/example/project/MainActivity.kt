package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import org.example.project.View.App
import org.example.project.ViewModel.DetailPageViewModel
import org.example.project.ViewModel.HomeViewModel
import org.example.project.ViewModel.ListsViewModel
import org.example.project.ViewModel.SearchViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
// Crée tes VM *une seule fois* et conserve-les via remember
            val homeVM = remember { HomeViewModel() }
            val searchVM = remember { SearchViewModel() }
            val listsVM = remember { ListsViewModel() }
            val detailVM = remember { DetailPageViewModel() }

            App(
                homeViewModel = homeVM,
                searchViewModel = searchVM,
                listsViewModel = listsVM,
                detailPageViewModel = detailVM

            )
        }
    }


    @Preview
    @Composable
    fun AppAndroidPreview() {
        // Crée tes VM *une seule fois* et conserve-les via remember
        val homeVM = remember { HomeViewModel() }
        val searchVM = remember { SearchViewModel() }
        val listsVM = remember { ListsViewModel() }
        val detailVM = remember { DetailPageViewModel() }

        App(
            homeViewModel = homeVM,
            searchViewModel = searchVM,
            listsViewModel = listsVM,
            detailPageViewModel = detailVM

        )
    }
}
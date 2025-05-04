package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.navigation.compose.rememberNavController
import org.example.project.View.App
import org.example.project.ViewModel.DetailPageViewModel
import org.example.project.ViewModel.HomeViewModel
import org.example.project.ViewModel.ListsViewModel
import org.example.project.ViewModel.SearchViewModel

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Gestionnaire de films") {
        // merci chatgpt wow
        val homeVM    = remember { HomeViewModel() }
        val searchVM  = remember { SearchViewModel() }
        val listsVM   = remember { ListsViewModel() }
        val detailVM = remember { DetailPageViewModel() }

        App(
            homeViewModel    = homeVM,
            searchViewModel  = searchVM,
            listsViewModel   = listsVM,
            detailPageViewModel = detailVM

        )
    }
}

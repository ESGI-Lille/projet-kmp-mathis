package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.View.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Art-kotlin",
    ) {
        App()
    }
}
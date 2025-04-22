package org.example.project.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.shadow
import androidx.navigation.NavController
import art_kotlin.composeapp.generated.resources.Res
import art_kotlin.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmallTopAppBar(navController: NavController) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 4.dp, shape = RectangleShape),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(24.dp)
                )
                Button(
                    onClick = {
                        navController.navigate("home")
                    }
                ) {
                    Text("home")
                }
            }
        }
    )
}
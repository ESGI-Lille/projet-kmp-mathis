package org.example.project.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import art_kotlin.composeapp.generated.resources.Res
import art_kotlin.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview



@Composable
@Preview
fun CardTableau(imageResource: DrawableResource) {
    Card {
        Column  {
            Row {
                Text(text = "Leonard de Vinci")
            }
            Row {
                Image(
                    painter = painterResource(imageResource),
                    contentDescription = null,
                    Modifier.size(350.dp)
                )
            }
            Row {
                Text(
                    text = "1872",
                    modifier = Modifier.padding(24.dp)
                )
                Text(
                    text = " affiché au musée d'Orsay",
                    modifier = Modifier.padding(24.dp)
                )
            }
        }
    }
}


@Composable
@Preview
fun Container(composeMultiplatform: Any?) {
    LazyColumn(
        modifier = Modifier.padding(15.dp)
    ) {
        val itemsList = listOf(
            Res.drawable.compose_multiplatform,
            Res.drawable.compose_multiplatform,
            Res.drawable.compose_multiplatform,
            Res.drawable.compose_multiplatform
        )
        items(itemsList) { item ->
            CardTableau(imageResource = item)
        }
    }
}
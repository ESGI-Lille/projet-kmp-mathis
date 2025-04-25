package org.example.project.View

import ApiReponsePerson
import ApiResponseObject
import Record
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import org.example.project.ViewModel.HomeViewModel


@Composable
fun CardTableau(record: Record) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Période et siècle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (record.period != null) {
                Text(
                    text = record.period,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 14.sp
                )
            }
            if (record.images.isNotEmpty()) {
                AsyncImage(
                    model = record.images.first().baseimageurl,
                    contentDescription = record.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    onError = { error ->
                        println(error)
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (record.century != null) {
                Text(
                    text = record.century,
                    style = MaterialTheme.typography.bodyMedium.copy(fontStyle = FontStyle.Italic),
                    fontSize = 14.sp
                )
            }
        }
        // Culture
        if (record.culture != null) {
            Text(
                text = record.culture,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
            )
        }
        // Date de création
        Text(
            text = "Créé le: ${record.createDate}",
            style = MaterialTheme.typography.bodySmall,
            fontSize = 12.sp,
        )
    }
}


@Composable
fun Container(homeViewModel: HomeViewModel = viewModel()) {
    val collectionData: ApiResponseObject? by homeViewModel.data.collectAsState()
    val authorData: ApiReponsePerson? by homeViewModel.authorData.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val error by homeViewModel.error.collectAsState()


    if (collectionData == null && authorData == null && !isLoading && error.isNullOrEmpty()) {
        LaunchedEffect(Unit) {
            homeViewModel.getRandomAuthorAndItsCollections()
            println("OOOO");
        }
    }

    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            if (isLoading) {
                CircularProgressIndicator()
            } else if (error != null) {
                Text(text = "Erreur : $error", color = MaterialTheme.colorScheme.error)
            } else if (authorData != null) {
                Text(
                    text = authorData!!.displayname,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            } else {
                Text(text = "Erreur : $error", color = MaterialTheme.colorScheme.error)
                println("Erreur dans Container: $error")
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        collectionData?.let {
            items(it.records) { item ->
                CardTableau(record = item)
            }
        }
    }
}

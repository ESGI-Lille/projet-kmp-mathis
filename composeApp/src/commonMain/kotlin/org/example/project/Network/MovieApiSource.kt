package org.example.project.Network

import ApiResponseObject
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json
import org.example.project.Network.Entity.MovieDetail

import java.util.Random

// Le random pour la page et les films
val random = Random()
fun rand(from: Int, to: Int): Int {
    return random.nextInt(to - from) + from
}

const val apiKey = "d4e2d76de72776487370ac5a45dc8d35";

suspend fun getRandomMovies(): ApiResponseObject {
    val randomPage = rand(1, 500)

    try {
        val response: HttpResponse = client.get(
            "https://api.themoviedb.org/3/discover/movie?api_key=$apiKey&page=$randomPage"
        )

        // Vérifier le statut de la réponse HTTP
        if (!response.status.isSuccess()) {
            val errorBody = response.body<String>()
            println("API_ERROR: Erreur HTTP ${response.status}: $errorBody")
            return ApiResponseObject(null, emptyList(), null)
        }

        val responseBody: String = response.body()
        println("API_RESPONSE: Réponse API brute: $responseBody")

        val json = Json { ignoreUnknownKeys = true }
        return json.decodeFromString(ApiResponseObject.serializer(), responseBody)

    } catch (e: Exception) {
        println("API_ERROR: ${e.message}")
        return ApiResponseObject(null, emptyList(), null)
    }
}

suspend fun getMovieData(movieId: Int): MovieDetail {


    val response: HttpResponse = client.get(
        "https://api.themoviedb.org/3/movie/${movieId}?api_key=$apiKey"
    )
    val responseBody: String = response.body()
    println("API_RESPONSE: Réponse API brute: $responseBody")
    return response.body();
}

package org.example.project.Network

import ApiResponseObject
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.isSuccess
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import org.example.project.Network.Entity.MovieDetail
import org.example.project.getPlatform
import java.util.Random


// Le random pour la page et les films
private val random = Random()
fun rand(from: Int, to: Int): Int = random.nextInt(to - from) + from

// clé api
// J'ai mis directement la clé api des les URL j'ai pas réussi à utiliser le defaultRequest
const val apiKey = "d4e2d76de72776487370ac5a45dc8d35"

// détermine l’URL selon la plateforme
val localBaseUrl: String by lazy {
    when (getPlatform().name) {
        "Desktop"  -> "http://localhost:3000"
        else       -> "http://10.0.2.2:3000"
    }
}


suspend fun getRandomMovies(): ApiResponseObject {
    val randomPage = rand(1, 500)
    return try {
        val response: HttpResponse = client.get(
            "https://api.themoviedb.org/3/discover/movie?api_key=$apiKey&page=$randomPage"
        )
        if (!response.status.isSuccess()) {
            val errorBody = response.body<String>()
            println("API_ERROR: Erreur HTTP ${response.status}: $errorBody")
            ApiResponseObject(null, emptyList(), null)
        } else {
            val responseBody: String = response.body()
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString(ApiResponseObject.serializer(), responseBody)
        }
    } catch (e: Exception) {
        println("API_ERROR: ${e.message}")
        ApiResponseObject(null, emptyList(), null)
    }
}


/**
 * Recupère les données d'un film via son ID
 *
 * */
suspend fun getMovieData(movieId: Int): MovieDetail {
    val response: HttpResponse = client.get(
        "https://api.themoviedb.org/3/movie/$movieId?api_key=$apiKey"
    )
    val responseBody: String = response.body()
    println("API_RESPONSE: Réponse API brute: $responseBody")
    return Json { ignoreUnknownKeys = true }.decodeFromString(MovieDetail.serializer(), responseBody)
}

/**
 * Cherche les films avec comme paramètre la valeur de query
 * */
suspend fun searchMovies(query: String): ApiResponseObject {
    val encodedQuery = java.net.URLEncoder.encode(query, "UTF-8")
    return try {
        val response: HttpResponse = client.get(
            "https://api.themoviedb.org/3/search/movie?api_key=$apiKey&query=$encodedQuery"
        )
        if (!response.status.isSuccess()) {
            val errorBody = response.body<String>()
            println("API_ERROR: Recherche HTTP ${response.status}: $errorBody")
            ApiResponseObject(null, emptyList(), null)
        } else {
            val body: String = response.body()
            val json = Json { ignoreUnknownKeys = true }
            json.decodeFromString(ApiResponseObject.serializer(), body)
        }
    } catch (e: Exception) {
        println("API_ERROR: ${e.message}")
        ApiResponseObject(null, emptyList(), null)
    }
}

/**
 * Ajoute un film à la watchlist locale via JSON Server
 */
suspend fun addToWatchlistLocal(movieDetail: MovieDetail): Boolean {
    return try {
        val response: HttpResponse = client.post("$localBaseUrl/watchlist") {
            contentType(ContentType.Application.Json)
            setBody(movieDetail)
        }
        response.status.isSuccess()
    } catch (e: Exception) {
        println("Local API watchlist error: ${e.message}")
        false
    }
}

/**
 * Ajoute un film aux favoris locaux via JSON Server
 */
suspend fun addToFavoritesLocal(movieDetail: MovieDetail): Boolean {
    return try {
        val response: HttpResponse = client.post("$localBaseUrl/favorites") {
            contentType(ContentType.Application.Json)
            setBody(movieDetail)
        }
        response.status.isSuccess()
    } catch (e: Exception) {
        println("Local API favorites error: ${e.message}")
        false
    }
}


/**
 * Récupère la liste de la watchlist locale
 */
suspend fun getWatchlistLocal(): List<MovieDetail> {
    return try {
        client.get("$localBaseUrl/watchlist").body()
    } catch (e: Exception) {
        println("Local API get watchlist error: ${e.message}")
        emptyList()
    }
}

/**
 * Récupère la liste des favoris locaux
 */
suspend fun getFavoritesLocal(): List<MovieDetail> {
    return try {
        client.get("$localBaseUrl/favorites").body()
    } catch (e: Exception) {
        println("Local API get favorites error: ${e.message}")
        emptyList()
    }
}

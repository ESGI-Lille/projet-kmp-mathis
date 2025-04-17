package org.example.project.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/** Le HttpClient est idéalement instancié une seule fois (singleton ou injection de dépendances). */
private val globalHttpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            useAlternativeNames = false
        })
    }
}

class SpaceXApiDataSource {
    val httpClient = globalHttpClient
    suspend fun getAllLaunches(): List<Oeuvre> =
        httpClient.get("https://api.spacexdata.com/v5/launches").body()
}

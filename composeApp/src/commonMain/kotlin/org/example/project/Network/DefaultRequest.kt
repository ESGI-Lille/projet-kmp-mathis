package org.example.project.Network

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import io.ktor.client.plugins.HttpTimeout


val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            useAlternativeNames = false
        })
    }

    defaultRequest {
        headers {
            append("Accept", "application/json")
        }
    }

    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            when (exception) {
                is ClientRequestException -> {
                    throw exception // Rethrow the exception if you want to handle it elsewhere
                }
                is ServerResponseException -> {
                    throw exception // Rethrow the exception if you want to handle it elsewhere
                }
                is ResponseException -> {
                    throw exception // Rethrow the exception if you want to handle it elsewhere
                }
                else -> throw exception
            }
        }
    }
}

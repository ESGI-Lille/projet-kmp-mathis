package org.example.project.Network

import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json

suspend fun getData(): String {
    val response: HttpResponse = client.get(
        "https://api.harvardartmuseums.org/object?person=29231&apikey=7d7e91b1-0626-4f6c-bfcc-87f5fbef6847"
    )
    val responseBody: String = response.body()
    return responseBody
}

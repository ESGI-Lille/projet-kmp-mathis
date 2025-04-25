package org.example.project.Network

import ApiReponsePerson
import ApiResponseObject
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import kotlinx.serialization.json.Json


suspend fun getRandomAuthor(): ApiReponsePerson {
    val response: HttpResponse = client.get(
        "https://api.harvardartmuseums.org/person/29481?apikey=7d7e91b1-0626-4f6c-bfcc-87f5fbef6847"
    )
    val responseBody: String = response.body()

    val json = Json { ignoreUnknownKeys = true }
    return json.decodeFromString(ApiReponsePerson.serializer(), responseBody)
}


suspend fun getDataFromRandomAuthor(): ApiResponseObject {
    val response: HttpResponse = client.get(
        "https://api.harvardartmuseums.org/object?person=29481&apikey=7d7e91b1-0626-4f6c-bfcc-87f5fbef6847"
    )
    val responseBody: String = response.body()

    val json = Json { ignoreUnknownKeys = true }
    return json.decodeFromString(ApiResponseObject.serializer(), responseBody)
}





// https://api.harvardartmuseums.org/person/29481?apikey=7d7e91b1-0626-4f6c-bfcc-87f5fbef6847
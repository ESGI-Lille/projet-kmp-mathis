package org.example.project.Network.Entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Movie(
    @SerialName("genre_ids")
    val genreIds: List<Int>?,

    val id: Int,
    @SerialName("original_language")
    val originalLanguage: String?,

    @SerialName("original_title")
    val originalTitle: String?,

    val overview: String?,
    val popularity: Double?,
    @SerialName("poster_path")
    val posterPath: String?,

    @SerialName("release_date")
    val releaseDate: String?,

    val title: String?,

    @SerialName("vote_average")
    val voteAverage: Double?,

)
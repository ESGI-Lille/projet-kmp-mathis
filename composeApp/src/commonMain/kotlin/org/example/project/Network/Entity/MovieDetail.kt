package org.example.project.Network.Entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetail(
    val genres: List<Genre>?,
    val id: Int,
    val overview: String?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>,
    @SerialName("release_date")
    val releaseDate: String?,
    val tagline: String?,
    val title: String?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?
)

@Serializable
data class Genre(
    val id: Int?,
    val name: String?
)

@Serializable
data class ProductionCompany(
    val id: Int,
    @SerialName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerialName("origin_country")
    val originCountry: String?
)

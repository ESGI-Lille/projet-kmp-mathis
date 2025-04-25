package org.example.project.Network.Entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Info(
    @SerialName("totalrecordsperquery")
    val totalRecordsPerPage: Int,
    @SerialName("totalrecords")
    val totalRecords: Int,
    val pages: Int,
    val page: Int,
    @SerialName("responsetime")
    val responseTime: String,
)
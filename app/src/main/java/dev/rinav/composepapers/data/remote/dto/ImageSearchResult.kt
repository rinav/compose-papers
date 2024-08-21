package dev.rinav.composepapers.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ImageSearchResult(
    @SerialName("results")
    val images: List<PapersDto>,
    @SerialName("total")
    val total: Int,
    @SerialName("total_pages")
    val totalPages: Int
)
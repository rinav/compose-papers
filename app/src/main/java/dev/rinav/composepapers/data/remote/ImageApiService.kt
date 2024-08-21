package dev.rinav.composepapers.data.remote

import dev.rinav.composepapers.data.remote.dto.ImageSearchResult
import dev.rinav.composepapers.data.remote.dto.PapersDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ImageApiService {

    @GET("/photos")
    suspend fun getAllImages(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<List<PapersDto>>

    @GET("/photos/{id}")
    suspend fun getImage(
        @Path("id") imageId: String
    ): Response<PapersDto>

    @GET("/search/photos")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): Response<ImageSearchResult>
}
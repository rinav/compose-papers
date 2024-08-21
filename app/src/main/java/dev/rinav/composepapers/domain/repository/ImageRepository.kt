package dev.rinav.composepapers.domain.repository

import com.github.michaelbull.result.Result
import dev.rinav.composepapers.data.remote.PapersException
import dev.rinav.composepapers.domain.models.PaperImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getImages(): Flow<Result<List<PaperImage>, PapersException>>
    suspend fun getImage(imageId: String): Result<PaperImage, PapersException>
    fun searchImage(query: String): Flow<List<PaperImage>>
}
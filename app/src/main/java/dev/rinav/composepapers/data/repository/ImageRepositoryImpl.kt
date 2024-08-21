package dev.rinav.composepapers.data.repository

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.map
import dev.rinav.composepapers.core.C
import dev.rinav.composepapers.data.mapper.toPaperImageList
import dev.rinav.composepapers.data.remote.ImageApiService
import dev.rinav.composepapers.data.remote.PapersException
import dev.rinav.composepapers.data.remote.bodyOrError
import dev.rinav.composepapers.domain.models.PaperImage
import dev.rinav.composepapers.domain.repository.ImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import timber.log.Timber


class ImageRepositoryImpl(
    private val apiService: ImageApiService,
) : ImageRepository {

    override fun getImages(): Flow<Result<List<PaperImage>, PapersException>> {
        //return withContext(Dispatchers.IO) {
        return flow<Result<List<PaperImage>, PapersException>> {
            emit(apiService
                .getAllImages(page = 1, perPage = C.PER_PAGE_ITEMS)
                .bodyOrError()
                .map {
                    it.toPaperImageList()
                })
        }.onStart {
            Timber.d("getting images: started...")
        }.onEach {
            Timber.d("getting images: on each:: ${it.isOk}")
        }.onCompletion {
            Timber.d("getting images from api flow completed...")
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getImage(imageId: String): Result<PaperImage, PapersException> {
        TODO("Not yet implemented")
    }

    override fun searchImage(query: String): Flow<List<PaperImage>> {
        TODO("Not yet implemented")
    }
}
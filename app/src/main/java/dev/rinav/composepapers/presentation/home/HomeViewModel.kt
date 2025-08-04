package dev.rinav.composepapers.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.fold
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.rinav.composepapers.domain.models.PaperImage
import dev.rinav.composepapers.domain.repository.ImageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val imageRepository: ImageRepository
) : ViewModel() {

    //val homeImages: MutableState<List<PaperImage>> = mutableStateOf (emptyList())
    val homeImages: StateFlow<List<PaperImage>> = imageRepository
        .getImages()
        .map { result ->
            result.fold(
                success = { papers ->
                    papers.forEach { image ->
                        Timber.d("images: ${image.id} :: ${image.photographerName} :: ${image.imageUrlRegular}")
                    }
                    return@fold papers
                },
                failure = {
                    Timber.e("error in getting home images: $it")
                    return@fold emptyList<PaperImage>()
                }
            )
        }
        .catch { exception ->
            Timber.d(exception, "error in getting images from repository")
        }.stateIn(
            scope = viewModelScope,
            initialValue = emptyList(),
            started = SharingStarted.WhileSubscribed(5000),
        )
}

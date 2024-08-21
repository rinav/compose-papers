package dev.rinav.composepapers.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import dev.rinav.composepapers.R
import dev.rinav.composepapers.domain.models.PaperImage
import dev.rinav.composepapers.presentation.components.ImageVerticalGrid
import dev.rinav.composepapers.presentation.components.PaperAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    images: State<List<PaperImage>>,
    onItemClick: (PaperImage) -> Unit,
) {
    //val paperImages:MutableState<List<PaperImage>> = mutableStateOf(images.value)
//    LaunchedEffect(key1 = images) {
//        images.value.forEach { image->
//            Timber.d("images: ${image.id} :: ${image.photographerName} :: ${image.imageUrlRegular}")
//        }
//    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            PaperAppBar(
                scrollBehavior = scrollBehavior,
                title = LocalContext.current.getString(R.string.app_name),
                upAvailable = false,
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search"
                        )
                    }
                }
            )

            ImageVerticalGrid(
                modifier = modifier,
                images = images.value,
                onItemClick = onItemClick,
            )
        }
    }
}
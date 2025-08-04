package dev.rinav.composepapers.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.rinav.composepapers.domain.models.PaperImage

@Composable
fun ImageVerticalGrid(
    modifier: Modifier = Modifier,
    images: List<PaperImage>,
    onItemClick: (PaperImage) -> Unit,
) {
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Fixed(count = 2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalItemSpacing = 16.dp
    ) {
        items(count = images.size) { index ->
            val image = images[index]
            ImageCard(
                image = image, modifier = Modifier,
                loadThumbnail = true,
                contentScale = ContentScale.Crop,
                isFavorite = false,
                onItemClick = onItemClick,
                onToggleFavStatus = {},
            )
        }
    }
}
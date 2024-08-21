package dev.rinav.composepapers.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.rinav.composepapers.domain.models.PaperImage

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: PaperImage,
    loadThumbnail: Boolean,
    contentScale:ContentScale,
    isFavorite: Boolean,
    onItemClick: (PaperImage) -> Unit,
    onToggleFavStatus: () -> Unit
) {
    val aspectRatio: Float by remember {
        derivedStateOf { (image?.width?.toFloat() ?: 1f) / (image?.height?.toFloat() ?: 1f) }
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
        onClick = { onItemClick(image) },
    ) {
        Box(modifier = Modifier) {
            AsyncPaperImage(
                modifier = Modifier.fillMaxSize(),
                loadThumbnail = loadThumbnail,
                thumbnailUrl = image.imageUrlSmall,
                fullImageUrl = image.imageUrlRegular,
                // damage the url to check if thumb is loading when there is an error
                // fullImageUrl = image.imageUrlRegular, //.replace("photo-", ""),
                contentScale = contentScale,
                contentDescription = "image"
            )

            FavoriteButton(
                modifier = Modifier.align(Alignment.BottomEnd),
                isFavorite = isFavorite,
                onFavClick = onToggleFavStatus
            )
        }
    }
}
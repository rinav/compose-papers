package dev.rinav.composepapers.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.rinav.composepapers.domain.models.PaperImage
import kotlinx.coroutines.delay

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    image: PaperImage,
    loadThumbnail: Boolean,
    contentScale: ContentScale,
    isFavorite: Boolean,
    onItemClick: (PaperImage) -> Unit,
    onToggleFavStatus: () -> Unit
) {
    val aspectRatio: Float by remember {
        derivedStateOf { image.width.toFloat() / image.height.toFloat() }
    }
    var isClicked by remember { mutableStateOf(false) }
    val scale = animateFloatAsState(
        targetValue = if (isClicked) 0.95f else 1f, // Slightly smaller on click
        animationSpec = tween(durationMillis = 100)
    )

    // LaunchedEffect *outside* the clickable modifier
    LaunchedEffect(isClicked) { // Key on isClicked
        if (isClicked) {
            delay(100) // Match animation duration
            isClicked = false // Reset the color
            onItemClick(image)
        }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio)
            .scale(scale.value) // Apply scale change,
            .then(modifier),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
        onClick = {
            isClicked = true
        },
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
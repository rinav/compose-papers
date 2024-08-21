package dev.rinav.composepapers.presentation.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest


@Composable
fun AsyncPaperImage(
    modifier: Modifier = Modifier,
    thumbnailUrl: String,
    fullImageUrl: String,
    loadThumbnail: Boolean,
    contentDescription: String?,
    contentScale: ContentScale,
) {
    val context = LocalContext.current

    val placeholderPainter = rememberAsyncImagePainter(thumbnailUrl)

    val asyncImage: ImageRequest = ImageRequest
        .Builder(context = context)
            .data(if (loadThumbnail) thumbnailUrl else fullImageUrl)
            .placeholder(placeholderPainter.request.placeholder)
            //.error(R.drawable.ic_launcher_background)
            .crossfade(true)
            .diskCacheKey(if (loadThumbnail) thumbnailUrl else fullImageUrl)
            .memoryCacheKey(if (loadThumbnail) thumbnailUrl else fullImageUrl)
            .placeholderMemoryCacheKey(thumbnailUrl)
            .memoryCachePolicy(policy = CachePolicy.DISABLED)
            .build()

    AsyncImage(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp)),
        model = asyncImage,
        error = placeholderPainter,
        contentDescription = contentDescription,
        contentScale = contentScale
    )
}
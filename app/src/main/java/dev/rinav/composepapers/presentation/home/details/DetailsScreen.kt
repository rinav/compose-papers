package dev.rinav.composepapers.presentation.home.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import dev.rinav.composepapers.R
import dev.rinav.composepapers.domain.models.PaperImage
import dev.rinav.composepapers.presentation.components.DetailInfo
import dev.rinav.composepapers.presentation.components.ImageCard
import dev.rinav.composepapers.presentation.components.PaperAppBar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    image: PaperImage?,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    onBackClick: () -> Unit,
    onPhotographerClick: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier.fillMaxSize().then(modifier),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PaperAppBar(
                scrollBehavior = scrollBehavior,
                title = LocalContext.current.getString(R.string.app_name),
                upAvailable = true,
                onUpClicked = onBackClick,
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "search"
                        )
                    }
                }
            )

            ImageCard(
                loadThumbnail = false,
                image = image!!,
                contentScale = ContentScale.FillHeight,
                isFavorite = false,
                onItemClick = {},
                onToggleFavStatus = {}
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(16.dp)
                    ),
            ) {
                DetailInfo(type = "ID", value = image.id.orEmpty())
                HorizontalDivider()

                DetailInfo(type = "Author", value = image.photographerName.orEmpty())
                HorizontalDivider()

                DetailInfo(type = "Size", value = "${image.width} x ${image.height}")
                HorizontalDivider()

                DetailInfo(type = "Created At", value = image.photographerUsername.orEmpty())
            }
        }
    }
}
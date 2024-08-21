package dev.rinav.composepapers.core.navigation

import dev.rinav.composepapers.domain.models.PaperImage
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes(val route: String) {
    @Serializable
    data object Home : Routes("home")

    @Serializable
    data object Favorites : Routes("favorites")

    @Serializable
    data object Search : Routes("search")

    @Serializable
    data class DetailsScreen(val image: PaperImage?): Routes("image")

    @Serializable
    data class ProfileScreen(val encodedProfileLink: String): Routes("profile/$encodedProfileLink")
}
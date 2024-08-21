package dev.rinav.composepapers.core.navigation

import android.net.Uri
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import dev.rinav.composepapers.domain.models.PaperImage
import dev.rinav.composepapers.presentation.favorites.FavoritesScreen
import dev.rinav.composepapers.presentation.home.HomeScreen
import dev.rinav.composepapers.presentation.home.HomeViewModel
import dev.rinav.composepapers.presentation.home.details.DetailsScreen
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PapersNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.Home.route,
        enterTransition = {
            fadeIn(animationSpec = tween(400)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start
            )
        },
        popEnterTransition = {
            fadeIn(animationSpec = tween(400)) + slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.End
            )
        }
    ) {
        composable(route = Routes.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val homeImages = homeViewModel.homeImages.collectAsState()
            HomeScreen(
                modifier = Modifier,
                scrollBehavior = scrollBehavior,
                images = homeImages,
                onItemClick = { image ->
                    navController.currentBackStackEntry?.arguments?.apply {
                        putParcelable("image", image)
                    }
                    Timber.d("putParcelable: ${image.id}")
                    navController.navigate(image)
                },
            )

        }

        composable(route = Routes.Favorites.route) {
            FavoritesScreen(
                scrollBehavior = scrollBehavior
            )
        }

        composable<PaperImage> { backStackEntry ->
            val image: PaperImage = backStackEntry.toRoute()

            DetailsScreen(
                image = image,
                scrollBehavior = scrollBehavior,
                onBackClick = { navController.navigateUp() },
                onPhotographerClick = { profileLink ->
                    val encodedProfileLink = Uri.encode(profileLink)
                    val route = Routes.ProfileScreen(encodedProfileLink).route
                    Timber.d("Constructed route: $route")
                    navController.navigate(route)
                },
            )
        }
    }
}
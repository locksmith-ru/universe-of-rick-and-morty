package edu.bedaev.universeofrickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.bedaev.universeofrickandmorty.ui.screen.characters.CharactersScreen
import edu.bedaev.universeofrickandmorty.ui.screen.episodes.EpisodesScreen
import edu.bedaev.universeofrickandmorty.ui.screen.locations.LocationsScreen
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    adaptiveParams: Pair<NavigationType, ContentType>
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        navTabScreens.find { it.route == currentDestination?.route } ?: Characters
    NavHost(
        navController = navController,
        startDestination = Characters.route,
        modifier = modifier
    ) {

        composable(
            route = Characters.route
        ) {
            CharactersScreen(
                navController = navController,
                currentScreen = currentScreen,
                adaptiveParams = adaptiveParams
            )
        }
        composable(
            route = Locations.route
        ) {
            LocationsScreen(
                navController = navController,
                currentScreen = currentScreen,
                adaptiveParams = adaptiveParams
            )
        }
        composable(
            route = Episodes.route
        ) {
            EpisodesScreen(
                navController = navController,
                currentScreen = currentScreen,
                adaptiveParams = adaptiveParams
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(
            this@navigateSingleTopTo.graph.findStartDestination().id
        ) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }

/*
enterTransition = {
                slideIn(
                    animationSpec = tween(
                        durationMillis = slideDuration,
                        easing = LinearEasing
                    )
                ) { IntOffset(it.width / 4, 100) } +
                        fadeIn(animationSpec = tween(teenDuration))
            }, exitTransition = {
                slideOut(
                    animationSpec = tween(
                        durationMillis = slideDuration,
                        easing = LinearEasing
                    )
                ) { IntOffset(-180, 50) } +
                        fadeOut(animationSpec = tween(teenDuration))
            }
 */
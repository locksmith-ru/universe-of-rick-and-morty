package edu.bedaev.universeofrickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    NavHost(
        navController = navController,
        startDestination = Characters.route,
        modifier = modifier
    ) {
        composable(route = Characters.route) {
            CharactersScreen(navController = navController, adaptiveParams = adaptiveParams)
        }
        composable(route = Locations.route) {
            LocationsScreen(navController = navController, adaptiveParams = adaptiveParams)
        }
        composable(route = Episodes.route) {
            EpisodesScreen(navController = navController, adaptiveParams = adaptiveParams)
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
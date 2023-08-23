package edu.bedaev.universeofrickandmorty.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.bedaev.universeofrickandmorty.domain.model.Episode
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.ui.screen.characters.CharacterDetailsScreen
import edu.bedaev.universeofrickandmorty.ui.screen.characters.CharactersScreen
import edu.bedaev.universeofrickandmorty.ui.screen.episodes.EpisodeDetailsScreen
import edu.bedaev.universeofrickandmorty.ui.screen.episodes.EpisodesScreen
import edu.bedaev.universeofrickandmorty.ui.screen.locations.LocationDetailsScreen
import edu.bedaev.universeofrickandmorty.ui.screen.locations.LocationsScreen
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    screenParams: Pair<NavigationType, ContentType>
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
                screenParams = screenParams
            )
        }
        composable(
            route = Locations.route
        ) {
            LocationsScreen(
                navController = navController,
                currentScreen = currentScreen,
                screenParams = screenParams
            )
        }
        composable(
            route = Episodes.route
        ) {
            EpisodesScreen(
                navController = navController,
                currentScreen = currentScreen,
                screenParams = screenParams
            )
        }

        composable(
            route = CharacterDetails.route
        ) {
            val result = navController
                .previousBackStackEntry?.savedStateHandle
                ?.get<Person>(key = CharacterDetails.personArgKey)
            result?.let { person ->
                CharacterDetailsScreen(
                    person = person,
                    onBackPressed = { navController.popBackStack() },
                    onEpisodeClicked = { id ->
                        // todo need transition to single episode screen
                        Log.d("_NavHost", "onEpisode clicked=$id")
                    }
                )
            }
        }

        composable(
            route = EpisodeDetails.route
        ) {
            val result = navController
                .previousBackStackEntry?.savedStateHandle
                ?.get<Episode>(key = EpisodeDetails.episodeArgKey)
            result?.let { episode ->
                EpisodeDetailsScreen(
                    episode = episode,
                    onBackPressed = { navController.popBackStack() },
                    onCharacterClicked = { id ->
                        // todo need transition to character details screen
                        Log.d("_NavHost", "onCharacter clicked=$id")
                    }
                )
            }
        }

        composable(
            route = LocationDetails.route
        ){
            val result = navController.previousBackStackEntry
                ?.savedStateHandle?.get<Location>(key = LocationDetails.locationArgKey)
            result?.let { location ->
                LocationDetailsScreen(
                    location = location,
                    onBackPressed = { navController.popBackStack() },
                    onItemClicked = { id ->
                        // todo need transition to character details screen
                        Log.d("_NavHost", "onCharacter clicked=$id")
                    }
                )
            }
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
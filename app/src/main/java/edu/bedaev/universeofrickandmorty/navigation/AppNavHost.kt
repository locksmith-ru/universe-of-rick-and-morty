package edu.bedaev.universeofrickandmorty.navigation

import android.util.Log
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
            route = CharacterDetails.routeWithArguments,
            arguments = CharacterDetails.arguments
        ) { backStackEntry ->
            val person = navController
                .previousBackStackEntry?.savedStateHandle
                ?.get<Person>(key = CharacterDetails.personArgKey)
            val personId = backStackEntry.arguments?.getInt(CharacterDetails.personIdArgKey)

            if (personId != null && personId > 0) {
                CharacterDetailsScreen(
                    personId = personId,
                    contentType = screenParams.second,
                    onBackPressed = { navController.popBackStack() },
                    onEpisodeClicked = { episodeId ->
                        // transition to single episode
                        navController.navigate(EpisodeDetails.passId(episodeId = episodeId))
                        Log.d("_NavHost", "CharacterDetails nested onEpisode clicked=$episodeId")
                    }
                )
            } else if (person != null) {
                CharacterDetailsScreen(
                    person = person,
                    contentType = screenParams.second,
                    onBackPressed = { navController.popBackStack() },
                    onEpisodeClicked = { episodeId ->
                        // transition to single episode screen
                        navController.navigate(EpisodeDetails.passId(episodeId = episodeId))
                        Log.d("_NavHost", "CharacterDetails onEpisode clicked=$episodeId")
                    }
                )
            }
        }

        composable(
            route = EpisodeDetails.routeWithArguments,
            arguments = EpisodeDetails.arguments
        ) { backStackEntry ->
            val episode = navController
                .previousBackStackEntry?.savedStateHandle
                ?.get<Episode>(key = EpisodeDetails.episodeArgKey)
            val episodeId = backStackEntry.arguments?.getInt(EpisodeDetails.episodeIdArgKey)

            if (episodeId != null && episodeId > 0) {
                EpisodeDetailsScreen(
                    episodeId = episodeId,
                    contentType = screenParams.second,
                    onBackPressed = { navController.popBackStack() },
                    onItemClicked = { personId ->
                        // transition to character details screen
                        navController.navigate(CharacterDetails.passId(id = personId))
                        Log.d("_NavHost", "EpisodeDetails nested onCharacter clicked=$personId")
                    }
                )
            } else if (episode != null) {
                EpisodeDetailsScreen(
                    episode = episode,
                    contentType = screenParams.second,
                    onBackPressed = { navController.popBackStack() },
                    onCharacterClicked = { personId ->
                        // transition to character details screen
                        navController.navigate(CharacterDetails.passId(id = personId))
                        Log.d("_NavHost", "EpisodeDetails onCharacter clicked=$personId")
                    }
                )
            }
        }

        composable(
            route = LocationDetails.routeWithArguments,
            arguments = LocationDetails.arguments
        ) { backStackEntry ->
            val location = navController.previousBackStackEntry
                ?.savedStateHandle?.get<Location>(key = LocationDetails.locationArgKey)
            val locationId = backStackEntry.arguments?.getInt(LocationDetails.locationIdArgKey)

            if (locationId != null && locationId > 0) {
                LocationDetailsScreen(
                    locationId = locationId,
                    contentType = screenParams.second,
                    onBackPressed = { navController.popBackStack() },
                    onItemClicked = { personId ->
                        // transition to character details screen
                        navController.navigate(CharacterDetails.passId(id = personId))
                        Log.d("_NavHost", "LocationDetails nested onCharacter clicked=$personId")
                    }
                )
            } else if (location != null) {
                LocationDetailsScreen(
                    location = location,
                    contentType = screenParams.second,
                    onBackPressed = { navController.popBackStack() },
                    onItemClicked = { personId ->
                        // transition to character details screen
                        navController.navigate(CharacterDetails.passId(id = personId))
                        Log.d("_NavHost", "LocationDetails onCharacter clicked=$personId")
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

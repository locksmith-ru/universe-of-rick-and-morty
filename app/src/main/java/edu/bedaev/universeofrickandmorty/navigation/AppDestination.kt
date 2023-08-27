package edu.bedaev.universeofrickandmorty.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Screenshot
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType

interface AppDestination {
    val titleResId: Int
    val icon: ImageVector
    val route: String
}

object Characters : AppDestination {
    override val titleResId: Int = R.string.characters
    override val icon: ImageVector = Icons.Filled.PersonPin
    override val route: String = "characters"
}

object Locations : AppDestination {
    override val titleResId: Int = R.string.locations
    override val icon: ImageVector = Icons.Filled.LocationOn
    override val route: String = "locations"
}

object Episodes : AppDestination {
    override val titleResId: Int = R.string.episodes
    override val icon: ImageVector = Icons.Filled.Screenshot
    override val route: String = "episodes"
}

object CharacterDetails : AppDestination {
    const val contentTypeArgKey = "content_type"
    const val personIdArgKey = "person_id"
    const val personArgKey = "person_arg_key"

    override val titleResId: Int = R.string.about_title
    override val icon: ImageVector = Icons.Filled.Details
    override val route: String = "character_details"

    val routeWithArguments = "${route}?$personIdArgKey={${personIdArgKey}}"
    val arguments = listOf(
        navArgument(personIdArgKey) {
            type = NavType.IntType
            defaultValue = 0
        }
    )

    fun passId(id: Int): String {
        return "${route}?$personIdArgKey=$id"
    }
}

object EpisodeDetails : AppDestination {
    const val episodeArgKey: String = "episode_arg_key"
    const val episodeIdArgKey = "episode_id"

    override val titleResId: Int = 0
    override val icon: ImageVector = Icons.Default.Settings
    override val route: String = "episode_details"

    val routeWithArguments = "${route}?$episodeIdArgKey={${episodeIdArgKey}}"
    val arguments = listOf(
        navArgument(episodeIdArgKey) {
            type = NavType.IntType
            defaultValue = 0
        }
    )

    fun passId(episodeId: Int): String =
        "${route}?$episodeIdArgKey=$episodeId"
}

object LocationDetails : AppDestination {
    const val locationArgKey = "location _arg_key"
    const val locationIdArgKey = "location_id"

    override val titleResId: Int = R.string.about_title
    override val icon: ImageVector = Icons.Filled.Settings
    override val route: String = "location_details"

    val routeWithArguments = "${route}?$locationIdArgKey={${locationIdArgKey}}"
    val arguments = listOf(
        navArgument(locationIdArgKey) {
            type = NavType.IntType
            defaultValue = 0
        }
    )

    fun passId(locationId: Int): String =
        "${route}?$locationIdArgKey=$locationId"
}

val navTabScreens = listOf(Characters, Locations, Episodes)

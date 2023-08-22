package edu.bedaev.universeofrickandmorty.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Screenshot
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import edu.bedaev.universeofrickandmorty.R

interface AppDestination{
    val titleResId: Int
    val icon: ImageVector
    val route: String
}

object Characters : AppDestination{
    override val titleResId: Int = R.string.characters
    override val icon: ImageVector = Icons.Filled.PersonPin
    override val route: String = "characters"
}

object Locations: AppDestination{
    override val titleResId: Int = R.string.locations
    override val icon: ImageVector = Icons.Filled.LocationOn
    override val route: String = "locations"
}

object Episodes: AppDestination{
    override val titleResId: Int = R.string.episodes
    override val icon: ImageVector = Icons.Filled.Screenshot
    override val route: String = "episodes"
}

object CharacterDetails : AppDestination{
    override val titleResId: Int = R.string.about_title
    override val icon: ImageVector = Icons.Filled.Details
    override val route: String = "character_details"

    const val idArgKey = "character_id_arg"
    const val titleArgKey = "window_title_arg"

    val routeWithArgs = "${route}/{${idArgKey}}/{${titleArgKey}}"
    val arguments = listOf(
        navArgument(idArgKey){ type = NavType.IntType },
        navArgument(titleArgKey){ type = NavType.StringType }
    )

}

val navTabScreens = listOf(Characters, Locations, Episodes)

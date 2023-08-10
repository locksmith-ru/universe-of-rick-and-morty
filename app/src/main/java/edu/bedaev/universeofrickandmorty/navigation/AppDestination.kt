package edu.bedaev.universeofrickandmorty.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Screenshot
import androidx.compose.ui.graphics.vector.ImageVector

interface AppDestination{
    val icon: ImageVector
    val route: String
}

object Characters : AppDestination{
    override val icon: ImageVector = Icons.Filled.PersonPin
    override val route: String = "characters"
}

object Locations: AppDestination{
    override val icon: ImageVector = Icons.Filled.LocationOn
    override val route: String = "locations"
}

object Episodes: AppDestination{
    override val icon: ImageVector = Icons.Filled.Screenshot
    override val route: String = "episodes"
}

val navTabScreens = listOf(Characters, Locations, Episodes)

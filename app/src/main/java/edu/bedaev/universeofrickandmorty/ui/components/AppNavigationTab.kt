package edu.bedaev.universeofrickandmorty.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.bedaev.universeofrickandmorty.navigation.AppDestination
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.navigation.navTabScreens
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

@Composable
fun AppBottomNavigationBar(
    modifier: Modifier = Modifier,
    onTabSelected: (AppDestination) -> Unit = {},
    destinations: List<AppDestination> = navTabScreens,
    currentScreen: AppDestination = Characters
) {
    NavigationBar(modifier = modifier.fillMaxWidth()) {
        destinations.forEach{ destination ->
            NavigationBarItem(
                selected = currentScreen == destination,
                onClick = { onTabSelected(destination) },
                icon = {
                    Icon(imageVector = destination.icon, contentDescription = "")
                }
            )
        }
    }
}

@Composable
fun AppNavigationRail(
    modifier: Modifier = Modifier,
    onTabSelected: (AppDestination) -> Unit = {},
    destinations: List<AppDestination> = navTabScreens,
    currentScreen: AppDestination = Characters
) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        destinations.forEach{ destination ->
            NavigationRailItem(
                selected = currentScreen == destination,
                onClick = { onTabSelected(destination) },
                icon = {
                    Icon(imageVector = destination.icon, contentDescription = "")
                }
            )
        }
    }
}

@Preview(name = "Light", group = "components", showBackground = true)
@Preview(name = "Dark", group = "components", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewNavigationBottom() {
    AppTheme {
        Surface {
            AppBottomNavigationBar()
        }
    }
}

@Preview(name = "Light", group = "components", showBackground = true)
@Preview(name = "Dark", group = "components", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewNavigationRail() {
    AppTheme {
        Surface {
            AppNavigationRail()
        }
    }
}
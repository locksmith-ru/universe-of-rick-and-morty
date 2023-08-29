package edu.bedaev.universeofrickandmorty.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.bedaev.universeofrickandmorty.R
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
        destinations.forEach { destination ->
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
    onMenuDrawerClicked: () -> Unit = {},
    onTabSelected: (AppDestination) -> Unit = {},
    destinations: List<AppDestination> = navTabScreens,
    currentScreen: AppDestination = Characters
) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        NavigationRailItem(
            selected = false,
            onClick = onMenuDrawerClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.navigation_menu)
                )
            }
        )
        destinations.forEach { destination ->
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

@Composable
inline fun AppNavigationDrawer(
    modifier: Modifier = Modifier,
    crossinline onMenuDrawerClicked: () -> Unit = {},
    crossinline onTabSelected: (AppDestination) -> Unit = {},
    destinations: List<AppDestination> = navTabScreens,
    currentScreen: AppDestination = Characters
) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .fillMaxHeight()
            .background(color = MaterialTheme.colorScheme.inverseOnSurface)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(128.dp),
                painter = painterResource(id = R.drawable.icon),
                contentDescription = stringResource(id = R.string.app_name)
            )
            IconButton(onClick = { onMenuDrawerClicked() }) {
                Icon(
                    imageVector = Icons.Default.MenuOpen,
                    contentDescription = stringResource(id = R.string.navigation_menu)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_name).uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
        destinations.forEach { destination ->
            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(id = R.string.characters)
                    )
                },
                label = {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(id = destination.titleResId)
                    )
                },
                selected = currentScreen == destination,
                colors = NavigationDrawerItemDefaults.colors(unselectedBadgeColor = Color.Transparent),
                onClick = { onTabSelected(destination) })
        }
    }
}

@Preview(name = "Light", group = "components", showBackground = true)
@Preview(
    name = "Dark", group = "components", showBackground = true,
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
@Preview(
    name = "Dark", group = "components", showBackground = true,
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

@Preview(name = "Light", group = "components", showBackground = true)
@Preview(
    name = "Dark", group = "components", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewNavigationDrawer() {
    AppTheme {
        Surface {
            AppNavigationDrawer()
        }
    }
}
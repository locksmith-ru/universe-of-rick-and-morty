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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.navigation.AppDestination
import edu.bedaev.universeofrickandmorty.navigation.navTabScreens
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

@Composable
fun AppBottomNavigationBar(
    modifier: Modifier = Modifier,
    destinations: List<AppDestination>,
    onDrawerClicked: () -> Unit = {}
) {
    NavigationBar(modifier = modifier.fillMaxWidth()) {
        NavigationBarItem(selected = true,
            onClick = onDrawerClicked,
            icon = {
                Icon(imageVector = destinations[0].icon,
                    contentDescription = stringResource(id = R.string.characters))
            })
        NavigationBarItem(selected = false,
            onClick = onDrawerClicked,
            icon = {
                Icon(imageVector = destinations[1].icon,
                    contentDescription = stringResource(id = R.string.locations))
            })
        NavigationBarItem(selected = false,
            onClick = onDrawerClicked,
            icon = {
                Icon(imageVector = destinations[2].icon,
                    contentDescription = stringResource(id = R.string.episodes))
            })
    }
}

@Composable
fun AppNavigationRail(
    modifier: Modifier = Modifier,
    destinations: List<AppDestination>,
    onDrawerClicked: () -> Unit = {}
) {
    NavigationRail(modifier = modifier.fillMaxHeight()) {
        NavigationRailItem(
            selected = true,
            onClick =  onDrawerClicked ,
            icon = {
                Icon(
                    imageVector = destinations[0].icon,
                    contentDescription = stringResource(id = R.string.characters)
                )
            }
        )
        NavigationRailItem(
            selected = false,
            onClick =  onDrawerClicked ,
            icon = {
                Icon(
                    imageVector = destinations[1].icon,
                    contentDescription = stringResource(id = R.string.locations)
                )
            }
        )
        NavigationRailItem(
            selected = false,
            onClick =  onDrawerClicked ,
            icon = {
                Icon(
                    imageVector = destinations[2].icon,
                    contentDescription = stringResource(id = R.string.episodes)
                )
            }
        )
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
            AppBottomNavigationBar(destinations = navTabScreens)
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
            AppNavigationRail(destinations = navTabScreens)
        }
    }
}
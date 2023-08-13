package edu.bedaev.universeofrickandmorty.ui.screen.locations

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.navigation.Locations
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.AdaptiveScreenContent
import edu.bedaev.universeofrickandmorty.ui.components.LocationItem
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType

private const val TAG = "_LocationsScreen"

@Composable
fun LocationsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    adaptiveParams: Pair<NavigationType, ContentType> =
        Pair(NavigationType.BOTTOM_NAVIGATION, ContentType.LIST_ONLY)
) {
    val viewModel: LocationViewModel = viewModel()

    AdaptiveScreenContent(
        modifier = modifier,
        loadingState = viewModel.loadingState,
        listItem = { item ->
            LocationItem(
                location = item as Location,
                onItemClicked = { listItem ->
                    Log.d(TAG, "onLocationClicked: ${listItem.id}")
                }
            )
        },
        adaptiveParams = adaptiveParams,
        currentDestination = Locations,
        onError = { viewModel.loadContent() },
        onTabSelected = { dst ->
            navController.navigateSingleTopTo(dst.route)
        },
        // todo удалить
        onItemClicked = { item ->
            onItemClicked(item = item)
        }
    )
}

private fun onItemClicked(item: String) {
    Log.d(TAG, "onItemClicked: $item")
}
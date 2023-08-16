package edu.bedaev.universeofrickandmorty.ui.screen.locations

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.navigation.Locations
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.AdaptiveScreenContent
import edu.bedaev.universeofrickandmorty.ui.components.LocationItem
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.ErrorScreen
import edu.bedaev.universeofrickandmorty.ui.screen.LoadingScreen
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

    when(viewModel.loadingState){
        is AppLoadingState.Loading -> LoadingScreen()
        is AppLoadingState.Error -> ErrorScreen()
        is AppLoadingState.Success -> {

            val lazyPagingItems: LazyPagingItems<ListItem> =
                (viewModel.loadingState as AppLoadingState.Success).data.collectAsLazyPagingItems()

            AdaptiveScreenContent(
                modifier = modifier,
                pagingData = lazyPagingItems,
                listItemView = { item ->
                    LocationItem(
                        location = item as Location,
                        onItemClicked = { listItem -> onItemClicked(item = listItem) }
                    )
                },
                adaptiveParams = adaptiveParams,
                currentDestination = Locations,
                onError = { viewModel.loadContent() },
                onTabSelected = { dst ->
                    navController.navigateSingleTopTo(dst.route)
                }
            )
        }
    }
}

private fun onItemClicked(item: ListItem) {
    Log.d(TAG, "onItemClicked: ${item.id}")
}
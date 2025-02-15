package edu.bedaev.universeofrickandmorty.ui.screen.locations

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.navigation.AppDestination
import edu.bedaev.universeofrickandmorty.navigation.CONTENT_TYPE_ARG_KEY
import edu.bedaev.universeofrickandmorty.navigation.LocationDetails
import edu.bedaev.universeofrickandmorty.navigation.Locations
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.AdaptiveScreenContent
import edu.bedaev.universeofrickandmorty.ui.components.LocationItem
import edu.bedaev.universeofrickandmorty.ui.components.SearchBar
import edu.bedaev.universeofrickandmorty.ui.components.SearchWidgetState
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.ErrorScreen
import edu.bedaev.universeofrickandmorty.ui.screen.LoadingScreen
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType
import kotlinx.coroutines.flow.Flow

private const val TAG = "_LocationsScreen"

@Suppress("UNCHECKED_CAST")
@Composable
fun LocationsScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    currentScreen: AppDestination = Locations,
    screenParams: Pair<NavigationType, ContentType> =
        Pair(NavigationType.BOTTOM_NAVIGATION, ContentType.LIST_ONLY)
) {
    val viewModel: LocationViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            SearchBar(
                title = stringResource(id = currentScreen.titleResId),
                hint = stringResource(id = R.string.location_search_hint),
                searchWidgetState = viewModel.searchViewWidgetState.value,
                searchTextState = viewModel.searchTextState.value,
                onTextChange = { newQuery ->
                    viewModel.updateSearchTextState(newTextValue = newQuery)
                },
                onCloseClicked = {
                    viewModel.updateSearchWidgetState(newStateValue = SearchWidgetState.CLOSED)
                    viewModel.loadContent()
                },
                onSearchClicked = { query ->
                    viewModel.loadContent(name = query.ifEmpty { null })
                },
                onSearchTriggered = {
                    viewModel.updateSearchWidgetState(newStateValue = SearchWidgetState.OPENED)
                }
            )
        }
    ) { padValues ->
        when (viewModel.loadingState) {
            is AppLoadingState.Loading -> LoadingScreen()
            is AppLoadingState.Error -> ErrorScreen()
            is AppLoadingState.Success<*> -> {

                val lazyPagingItems: LazyPagingItems<ListItem> =
                    (viewModel.loadingState as AppLoadingState.Success<Flow<PagingData<ListItem>>>)
                        .data!!.collectAsLazyPagingItems()

                if (lazyPagingItems.itemCount > 0) {
                    AdaptiveScreenContent(
                        modifier = modifier.padding(padValues),
                        pagingData = lazyPagingItems,
                        listItemView = { item ->
                            LocationItem(
                                location = item as Location,
                                onItemClicked = { listItem ->
                                    onItemClicked(
                                        navController = navController,
                                        item = listItem,
                                        contentType = screenParams.second
                                    )
                                }
                            )
                        },
                        adaptiveParams = screenParams,
                        currentDestination = currentScreen,
                        onError = { viewModel.loadContent() },
                        onTabSelected = { dst ->
                            navController.navigateSingleTopTo(dst.route)
                        }
                    )
                }
            }
        }
    }
}

private fun onItemClicked(
    navController: NavHostController,
    item: ListItem,
    contentType: ContentType
) {
    navController.currentBackStackEntry
        ?.savedStateHandle?.set(key = LocationDetails.locationArgKey,
            value = item as Location)
    navController.currentBackStackEntry?.savedStateHandle
        ?.set(key = CONTENT_TYPE_ARG_KEY, value = contentType)
    navController.navigate(LocationDetails.route)
    Log.d(TAG, "onItemClicked: ${item.id}")
}
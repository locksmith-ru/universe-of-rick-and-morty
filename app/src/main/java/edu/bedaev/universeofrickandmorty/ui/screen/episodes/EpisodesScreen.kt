package edu.bedaev.universeofrickandmorty.ui.screen.episodes

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
import edu.bedaev.universeofrickandmorty.domain.model.Episode
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.navigation.AppDestination
import edu.bedaev.universeofrickandmorty.navigation.Episodes
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.AdaptiveScreenContent
import edu.bedaev.universeofrickandmorty.ui.components.EpisodeItem
import edu.bedaev.universeofrickandmorty.ui.components.SearchBar
import edu.bedaev.universeofrickandmorty.ui.components.SearchWidgetState
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.ErrorScreen
import edu.bedaev.universeofrickandmorty.ui.screen.LoadingScreen
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType
import kotlinx.coroutines.flow.Flow

private const val TAG = "_EpisodesScreen"

@Suppress("UNCHECKED_CAST")
@Composable
fun EpisodesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    currentScreen: AppDestination = Episodes,
    adaptiveParams: Pair<NavigationType, ContentType> =
        Pair(NavigationType.BOTTOM_NAVIGATION, ContentType.LIST_ONLY)
) {
    val viewModel: EpisodeViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            SearchBar(
                title = stringResource(id = currentScreen.titleResId),
                hint = stringResource(id = R.string.episodes_search_hint),
                searchWidgetState = viewModel.searchViewWidgetState.value,
                searchTextState = viewModel.searchTextState.value,
                onTextChange = { newQuery ->
                    viewModel.updateSearchTextState(newTextValue = newQuery)
                },
                onCloseClicked = {
                    viewModel.updateSearchWidgetState(newStateValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = { query ->
                    // todo create function
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
                        listItemView = { listItem ->
                            EpisodeItem(
                                episode = listItem as Episode,
                                onItemClicked = { item -> onItemClicked(item = item) }
                            )
                        },
                        adaptiveParams = adaptiveParams,
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

private fun onItemClicked(item: ListItem) {
    Log.d(TAG, "onItemClicked: ${item.id}")
}
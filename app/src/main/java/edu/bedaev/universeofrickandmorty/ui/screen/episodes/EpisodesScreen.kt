package edu.bedaev.universeofrickandmorty.ui.screen.episodes

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import edu.bedaev.universeofrickandmorty.domain.model.Episode
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.navigation.Episodes
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.AdaptiveScreenContent
import edu.bedaev.universeofrickandmorty.ui.components.EpisodeItem
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.ErrorScreen
import edu.bedaev.universeofrickandmorty.ui.screen.LoadingScreen
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType

private const val TAG = "_EpisodesScreen"

@Composable
fun EpisodesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    adaptiveParams: Pair<NavigationType, ContentType> =
        Pair(NavigationType.BOTTOM_NAVIGATION, ContentType.LIST_ONLY)
) {
    val viewModel: EpisodeViewModel = viewModel()

    when(viewModel.loadingState){
        is AppLoadingState.Loading -> LoadingScreen()
        is AppLoadingState.Error -> ErrorScreen()
        is AppLoadingState.Success -> {

            val lazyPagingItems: LazyPagingItems<ListItem> =
                (viewModel.loadingState as AppLoadingState.Success).data.collectAsLazyPagingItems()

            AdaptiveScreenContent(
                modifier = modifier,
                pagingData = lazyPagingItems,
                listItemView  = { listItem ->
                    EpisodeItem(
                        episode = listItem as Episode,
                        onItemClicked = { item -> onItemClicked(item = item) }
                    )
                },
                adaptiveParams = adaptiveParams,
                currentDestination = Episodes,
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
package edu.bedaev.universeofrickandmorty.ui.screen.characters

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
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
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.navigation.AppDestination
import edu.bedaev.universeofrickandmorty.navigation.CharacterDetails
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.AdaptiveScreenContent
import edu.bedaev.universeofrickandmorty.ui.components.CharacterItem
import edu.bedaev.universeofrickandmorty.ui.components.SearchBar
import edu.bedaev.universeofrickandmorty.ui.components.SearchWidgetState
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.ErrorScreen
import edu.bedaev.universeofrickandmorty.ui.screen.LoadingScreen
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType
import kotlinx.coroutines.flow.Flow

private const val TAG = "_CharactersScreen"

@Suppress("UNCHECKED_CAST")
@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    currentScreen: AppDestination = Characters,
    screenParams: Pair<NavigationType, ContentType> =
        Pair(NavigationType.BOTTOM_NAVIGATION, ContentType.LIST_ONLY)
) {
    val viewModel: CharactersViewModel = hiltViewModel()

    val searchWidgetState = viewModel.searchViewWidgetState
    val searchTextState = viewModel.searchTextState

    Scaffold(
        topBar = {
            SearchBar(
                title = stringResource(id = currentScreen.titleResId),
                hint = stringResource(id = R.string.character_search_hint),
                searchWidgetState = searchWidgetState.value,
                searchTextState = searchTextState.value,
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
            is AppLoadingState.Loading -> LoadingScreen(modifier = modifier.padding(padValues))
            is AppLoadingState.Error -> ErrorScreen(modifier = modifier.padding(padValues))
            is AppLoadingState.Success<*> -> {

                val lazyPagingItems: LazyPagingItems<ListItem> =
                    (viewModel.loadingState as AppLoadingState.Success<Flow<PagingData<ListItem>>>)
                        .data!!.collectAsLazyPagingItems()

                if (lazyPagingItems.itemCount > 0) {
                    AdaptiveScreenContent(
                        modifier = modifier.padding(padValues),
                        pagingData = lazyPagingItems,
                        listItemView = { item ->
                            CharacterItem(
                                person = item as Person,
                                onItemClicked = { listItem ->
                                    onItemClicked(
                                        navHostController = navController,
                                        item = listItem
                                    )
                                }
                            )
                            Divider()
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
    navHostController: NavHostController,
    item: ListItem
) {
    navHostController
        .navigateSingleTopTo("${CharacterDetails.route}/${item.id}/${item.name}")
}
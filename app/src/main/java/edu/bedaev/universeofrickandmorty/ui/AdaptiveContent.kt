package edu.bedaev.universeofrickandmorty.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import edu.bedaev.universeofrickandmorty.domain.model.Episode
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.navigation.AppDestination
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.navigation.Episodes
import edu.bedaev.universeofrickandmorty.navigation.Locations
import edu.bedaev.universeofrickandmorty.ui.components.AppBottomNavigationBar
import edu.bedaev.universeofrickandmorty.ui.components.AppFloatingActionButton
import edu.bedaev.universeofrickandmorty.ui.components.AppNavigationDrawer
import edu.bedaev.universeofrickandmorty.ui.components.AppNavigationRail
import edu.bedaev.universeofrickandmorty.ui.components.CharacterItem
import edu.bedaev.universeofrickandmorty.ui.components.EpisodeItem
import edu.bedaev.universeofrickandmorty.ui.components.LocationItem
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.ErrorScreen
import edu.bedaev.universeofrickandmorty.ui.screen.LoadingScreen
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun AdaptiveScreenContent(
    modifier: Modifier = Modifier,
    loadingState: AppLoadingState,
    listItemView: @Composable ((ListItem) -> Unit)? = null,
    adaptiveParams: Pair<NavigationType, ContentType> = Pair(
        NavigationType.BOTTOM_NAVIGATION,
        ContentType.LIST_ONLY
    ),
    currentDestination: AppDestination = Characters,
    onError: () -> Unit = {},
    onTabSelected: (AppDestination) -> Unit = {}
) {
    when (loadingState) {
        AppLoadingState.Loading -> LoadingScreen()
        AppLoadingState.Error -> ErrorScreen(onRetry = { onError() })
        is AppLoadingState.Success -> {
            AdaptiveScreenContent(
                modifier = modifier,
                loadingState = loadingState,
                listItemView = listItemView,
                adaptiveParams = adaptiveParams,
                currentDestination = currentDestination,
                onTabSelected = onTabSelected
            )
        }
    }
}

@Composable
private fun AdaptiveScreenContent(
    modifier: Modifier = Modifier,
    loadingState: AppLoadingState.Success,
    listItemView: @Composable ((ListItem) -> Unit)? = null,
    adaptiveParams: Pair<NavigationType, ContentType> = Pair(
        NavigationType.BOTTOM_NAVIGATION,
        ContentType.LIST_ONLY
    ),
    currentDestination: AppDestination = Characters,
    onTabSelected: (AppDestination) -> Unit = {}
) {
    // тип навигационного меню: нижнее, слева или выдвижная шторка
    val navType: NavigationType = adaptiveParams.first
    // тип списка: одиночный или две колонки
    val listType: ContentType = adaptiveParams.second

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (navType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(modifier = modifier, drawerContent = {
            PermanentDrawerSheet {
                AppNavigationDrawer(
                    modifier = Modifier,
                    onMenuDrawerClicked = { /*todo click on menu button */ },
                    onTabSelected = onTabSelected,
                    currentScreen = currentDestination,
                )
            }
        }) {
            AppContent(
                navType = navType,
                listType = listType,
                loadingState = loadingState,
                listItemView = listItemView,
                currentScreen = currentDestination,
                onTabSelected = onTabSelected
            )
        }
    } else {
        ModalNavigationDrawer(
            modifier = modifier, drawerContent = {
                ModalDrawerSheet {
                    AppNavigationDrawer(
                        modifier = Modifier,
                        onMenuDrawerClicked = {
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        onTabSelected = onTabSelected,
                        currentScreen = currentDestination,
                    )
                }
            }, drawerState = drawerState
        ) {
            AppContent(navType = navType,
                listType = listType,
                loadingState = loadingState,
                listItemView = listItemView,
                currentScreen = currentDestination,
                onTabSelected = onTabSelected,
                onDrawerMenuClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                })
        }
    }
}

@Composable
private fun AppContent(
    modifier: Modifier = Modifier,
    loadingState: AppLoadingState.Success,
    listItemView: @Composable ((ListItem) -> Unit)? = null,
    currentScreen: AppDestination = Characters,
    navType: NavigationType = NavigationType.BOTTOM_NAVIGATION,
    listType: ContentType = ContentType.LIST_ONLY,
    onDrawerMenuClicked: () -> Unit = {},
    onTabSelected: (AppDestination) -> Unit = {},
) {

    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navType == NavigationType.NAVIGATION_RAIL) {
            AppNavigationRail(
                modifier = Modifier,
                onMenuDrawerClicked = onDrawerMenuClicked,
                onTabSelected = onTabSelected
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val scrollState: ScrollableState
                val showFab: Boolean
                val data = loadingState.data.collectAsLazyPagingItems()
                if (listType == ContentType.LIST_ONLY) {
                    val lazyState = rememberLazyListState()
                    scrollState = lazyState
                    val showButton by remember { derivedStateOf { lazyState.firstVisibleItemIndex > 0 } }
                    showFab = showButton
                    // одноколоночный список
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
                        state = lazyState,
                    ) {
                        item { Spacer(modifier = Modifier.height(16.dp)) }
                        items(
                            count = data.itemCount,
                            key = data.itemKey(),
                            contentType = data.itemContentType()
                        ) { index ->
                            val item = data[index]
                            listItemView?.let {
                                it(item as ListItem)
                            }
                        }
                    }
                } else {
                    val lazyGridState = rememberLazyGridState()
                    scrollState = lazyGridState
                    val showButton by remember { derivedStateOf { lazyGridState.firstVisibleItemIndex > 0 } }
                    showFab = showButton
                    // двух колоночный список
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
                        state = lazyGridState
                    ) {
                        items(
                            count = data.itemCount,
                            key = data.itemKey(),
                            contentType = data.itemContentType()
                        ) { index ->
                            val item = data[index]
                            listItemView?.let {
                                it(item as ListItem)
                            }
                        }
                    }
                }
                // Floating Action Button
                SetFab(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 16.dp, end = 16.dp),
                    state = scrollState,
                    showButton = showFab
                )
            }
            AnimatedVisibility(
                visible = navType == NavigationType.BOTTOM_NAVIGATION
            ) {
                AppBottomNavigationBar(
                    modifier = Modifier,
                    onTabSelected = onTabSelected,
                    currentScreen = currentScreen
                )
            }

        }
    }
}

@Composable
private fun SetFab(
    modifier: Modifier, showButton: Boolean = true, state: ScrollableState
) {
    if (showButton) {
        val scope = rememberCoroutineScope()
        AppFloatingActionButton(
            modifier = modifier.navigationBarsPadding(),
            onClick = {
                scope.launch {
                    when (state) {
                        is LazyListState -> state.animateScrollToItem(0)
                        is LazyGridState -> state.animateScrollToItem(0)
                        else -> {}
                    }
                }
            })
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNormal() {
    AppTheme {
        Surface {
            AdaptiveScreenContent(
                loadingState = AppLoadingState.Success(
                    data = MutableStateFlow(
                        PagingData.from(
                            (1..100).map { Episode.fakeEpisode() }
                        )
                    )
                ),
                listItemView = { listItem ->
                    EpisodeItem(
                        episode = listItem as Episode
                    )
                },
                adaptiveParams = NavigationType.BOTTOM_NAVIGATION to ContentType.LIST_ONLY,
                currentDestination = Episodes
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 700)
@Composable
fun PreviewMedium() {
    AppTheme {
        Surface {
            AdaptiveScreenContent(
                loadingState = AppLoadingState.Success(
                    data = MutableStateFlow(
                        PagingData.from(
                            (1..100).map { Location.fakeLocation() }
                        )
                    )
                ),
                listItemView = { listItem ->
                    LocationItem(
                        location = listItem as Location
                    )
                },
                adaptiveParams = NavigationType.NAVIGATION_RAIL to ContentType.LIST_AND_DETAIL,
                currentDestination = Locations
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 1000)
@Composable
fun PreviewDesktop() {
    AppTheme {
        Surface {
            AdaptiveScreenContent(
                loadingState = AppLoadingState.Success(
                    data = MutableStateFlow(
                        PagingData.from(
                            (1..100)
                                .map { Person.fakePerson() }
                        )
                    )
                ),
                listItemView = { listItem ->
                    CharacterItem(
                        person = listItem as Person
                    )
                },
                adaptiveParams = NavigationType.PERMANENT_NAVIGATION_DRAWER to ContentType.LIST_AND_DETAIL,
                currentDestination = Characters
            )
        }
    }
}

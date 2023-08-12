package edu.bedaev.universeofrickandmorty.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import edu.bedaev.universeofrickandmorty.navigation.AppDestination
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.ui.components.AppBottomNavigationBar
import edu.bedaev.universeofrickandmorty.ui.components.AppFloatingActionButton
import edu.bedaev.universeofrickandmorty.ui.components.AppNavigationDrawer
import edu.bedaev.universeofrickandmorty.ui.components.AppNavigationRail
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.ErrorScreen
import edu.bedaev.universeofrickandmorty.ui.screen.LoadingScreen
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType
import kotlinx.coroutines.launch

@Composable
fun AdaptiveScreenContent(
    modifier: Modifier = Modifier,
    loadingState: AppLoadingState,
    adaptiveParams: Pair<NavigationType, ContentType> =
        Pair(NavigationType.BOTTOM_NAVIGATION, ContentType.LIST_ONLY),
    currentDestination: AppDestination = Characters,
    onError: () -> Unit = {},
    onTabSelected: (AppDestination) -> Unit = {},
    onItemClicked: (String) -> Unit = {}
) {
    when (loadingState) {
        AppLoadingState.Loading -> LoadingScreen()
        AppLoadingState.Error -> ErrorScreen(onRetry = { onError() })
        is AppLoadingState.Success -> {
            AdaptiveScreenContent(
                modifier = modifier,
                loadingState = loadingState,
                adaptiveParams = adaptiveParams,
                currentDestination = currentDestination,
                onTabSelected = onTabSelected,
                onItemClicked = onItemClicked
            )
        }

        else -> ErrorScreen(onRetry = { onError() })
    }
}

@Composable
private fun AdaptiveScreenContent(
    modifier: Modifier = Modifier,
    loadingState: AppLoadingState.Success,
    adaptiveParams: Pair<NavigationType, ContentType> =
        Pair(NavigationType.BOTTOM_NAVIGATION, ContentType.LIST_ONLY),
    currentDestination: AppDestination = Characters,
    onTabSelected: (AppDestination) -> Unit = {},
    onItemClicked: (String) -> Unit = {}
) {
    // тип навигационного меню: нижнее, слева или выдвижная шторка
    val navType: NavigationType = adaptiveParams.first
    // тип списка: одиночный или две колонки
    val listType: ContentType = adaptiveParams.second

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (navType == NavigationType.PERMANENT_NAVIGATION_DRAWER) {
        PermanentNavigationDrawer(
            modifier = modifier,
            drawerContent = {
                PermanentDrawerSheet {
                    AppNavigationDrawer(
                        modifier = Modifier,
                        onMenuDrawerClicked = { /*todo click on menu button */ },
                        onTabSelected = onTabSelected,
                        currentScreen = currentDestination,
                    )
                }
            }
        ) {
            AppContent(
                navType = navType,
                listType = listType,
                loadingState = loadingState,
                currentScreen = currentDestination,
                onTabSelected = onTabSelected,
                onItemClicked = onItemClicked
            )
        }
    } else {
        ModalNavigationDrawer(
            modifier = modifier,
            drawerContent = {
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
            },
            drawerState = drawerState
        ) {
            AppContent(
                navType = navType,
                listType = listType,
                loadingState = loadingState,
                currentScreen = currentDestination,
                onTabSelected = onTabSelected,
                onItemClicked = onItemClicked,
                onDrawerMenuClicked = {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    }
}

@Composable
private fun AppContent(
    modifier: Modifier = Modifier,
    loadingState: AppLoadingState.Success,
    currentScreen: AppDestination = Characters,
    navType: NavigationType = NavigationType.BOTTOM_NAVIGATION,
    listType: ContentType = ContentType.LIST_ONLY,
    onDrawerMenuClicked: () -> Unit = {},
    onTabSelected: (AppDestination) -> Unit = {},
    onItemClicked: (String) -> Unit = {}
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
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f)) {
                val scrollState: ScrollableState
                val showFab: Boolean
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
                        items(loadingState.data) { item ->
                            Text(
                                text = item.toString(),
                                modifier = Modifier.clickable { onItemClicked(item.toString()) }
                            )
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
                        items(loadingState.data) { item ->
                            Text(
                                text = item.toString(),
                                modifier = Modifier.clickable { onItemClicked(item.toString()) }
                            )
                        }
                    }
                }
                // Floating Action Button
                SetFab(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 16.dp, end = 16.dp),
                    state = scrollState, showButton = showFab
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
    modifier: Modifier,
    showButton: Boolean = true,
    state: ScrollableState
) {
    if (showButton) {
        val scope = rememberCoroutineScope()
        AppFloatingActionButton(
            modifier = modifier,
            onClick = {
                scope.launch {
                    when (state) {
                        is LazyListState -> state.animateScrollToItem(0)
                        is LazyGridState -> state.animateScrollToItem(0)
                        else -> {}
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNormal() {
    AppTheme {
        Surface {
            AdaptiveScreenContent(
                loadingState = AppLoadingState.Success(
                    data = (1..100)
                        .map { i ->
                            "Test element #${i}"
                        }
                )
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
                    data =
                    (1..101)
                        .map { i ->
                            "Element on medium device #${i}"
                        }
                ),
                adaptiveParams = NavigationType.NAVIGATION_RAIL to ContentType.LIST_AND_DETAIL
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
                    data = (1..100)
                        .map { i ->
                            "Element on desktop #${i}"
                        }
                ),
                adaptiveParams =
                NavigationType.PERMANENT_NAVIGATION_DRAWER to ContentType.LIST_AND_DETAIL
            )
        }
    }
}
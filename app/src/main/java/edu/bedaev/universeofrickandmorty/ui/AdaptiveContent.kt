package edu.bedaev.universeofrickandmorty.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.navigation.AppDestination
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.ui.components.AppBottomNavigationBar
import edu.bedaev.universeofrickandmorty.ui.components.AppFloatingActionButton
import edu.bedaev.universeofrickandmorty.ui.components.AppNavigationDrawer
import edu.bedaev.universeofrickandmorty.ui.components.AppNavigationRail
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType
import kotlinx.coroutines.launch

@Composable
fun AdaptiveScreenContent(
    modifier: Modifier = Modifier,
    pagingData: LazyPagingItems<ListItem>,
    listItemView: @Composable ((ListItem) -> Unit)? = null,
    adaptiveParams: Pair<NavigationType, ContentType> = Pair(
        NavigationType.BOTTOM_NAVIGATION,
        ContentType.LIST_ONLY
    ),
    currentDestination: AppDestination = Characters,
    onError: () -> Unit = { },
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
                data = pagingData,
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
                data = pagingData,
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
    data: LazyPagingItems<ListItem>,
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
                val showFab: State<Boolean>
                if (listType == ContentType.LIST_ONLY) {
                    scrollState = rememberLazyListState()
                    showFab = remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 } }
                    // одноколоночный список
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
                        state = scrollState,
                    ) {
                        // Загрузка элементов в методе refresh. Отображение надписи
                        if (data.loadState.refresh == LoadState.Loading) {
                            item {
                                Text(
                                    text = stringResource(id = R.string.please_wait),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                        // Загрузка элементов в методе append. Отображение индикатора
                        if (data.loadState.append == LoadState.Loading) {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }

                        items(count = data.itemCount) { index ->
                            val item = data[index]
                            listItemView?.let {
                                if (item != null)
                                    it(item)
                            }
                            Divider()
                        }
                    }
                } else {
                    scrollState = rememberLazyGridState()
                    showFab = remember { derivedStateOf { scrollState.firstVisibleItemIndex > 0 } }
                    // двух колоночный список
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxWidth(),
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
                        state = scrollState
                    ) {
                        // Загрузка элементов в методе refresh. Отображение надписи
                        if (data.loadState.refresh == LoadState.Loading) {
                            item {
                                Text(
                                    text = stringResource(id = R.string.please_wait),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                        // Загрузка элементов в методе append. Отображение индикатора
                        if (data.loadState.append == LoadState.Loading) {
                            item {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentWidth(Alignment.CenterHorizontally)
                                )
                            }
                        }
                        items(count = data.itemCount) { index ->
                            val item = data[index]
                            listItemView?.let {
                                it(item as ListItem)
                            }
                            Divider()
                        }
                    }
                }
                // Floating Action Button
                SetFab(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(bottom = 16.dp, end = 16.dp),
                    state = scrollState,
                    showButton = showFab.value
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
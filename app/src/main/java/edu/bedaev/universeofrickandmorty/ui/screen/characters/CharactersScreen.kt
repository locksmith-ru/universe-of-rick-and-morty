package edu.bedaev.universeofrickandmorty.ui.screen.characters

import android.util.Log
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.AdaptiveScreenContent
import edu.bedaev.universeofrickandmorty.ui.components.CharacterItem
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.ErrorScreen
import edu.bedaev.universeofrickandmorty.ui.screen.LoadingScreen
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType

private const val TAG = "_CharactersScreen"

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    adaptiveParams: Pair<NavigationType, ContentType> =
        Pair(NavigationType.BOTTOM_NAVIGATION, ContentType.LIST_ONLY)
) {
    val viewModel: CharactersViewModel = hiltViewModel()

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
                    CharacterItem(
                        person = item as Person,
                        onItemClicked = { listItem -> onItemClicked(item = listItem) }
                    )
                    Divider()
                },
                adaptiveParams = adaptiveParams,
                currentDestination = Characters,
                onError = { viewModel.loadContent() },
                onTabSelected = { dst ->
                    navController.navigateSingleTopTo(dst.route)
                }
            )
        }
    }


/*    AdaptiveScreenContent(
        modifier = modifier,
        loadingState = viewModel.loadingState,
        listItemView = { item ->
            CharacterItem(
                person = item as Person,
                onItemClicked = { listItem -> onItemClicked(item = listItem) }
            )
            Divider()
        },
        adaptiveParams = adaptiveParams,
        currentDestination = Characters,
        onError = { viewModel.loadContent() },
        onTabSelected = { dst ->
            navController.navigateSingleTopTo(dst.route)
        }
    )*/
}

private fun onItemClicked(item: ListItem) {
    Log.d(TAG, "onItemClicked: ${item.id}")
}

/*   val client = OkHttpClient.Builder()
        .addNetworkInterceptor(
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.BODY
            )
        )
        .followRedirects(true)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    val api = retrofit.create(CharactersApi::class.java)

    val pager = remember {
        Pager(
            config = PagingConfig(20, enablePlaceholders = true, maxSize = 200),
        ){ CharacterPagingSource(service = CharacterService(api)) }
    }

    val lazyPagingItems: LazyPagingItems<ListItem> = pager.flow.collectAsLazyPagingItems()*/


/*
 val lazyPagingItems: LazyPagingItems<ListItem> = viewModel.pagingDataFlow.collectAsLazyPagingItems()

    LazyColumn{

        if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
            item {
                Text(
                    text = "Паташти шушуть",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }

        if (lazyPagingItems.loadState.append == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }

        items(count = lazyPagingItems.itemCount) { index ->
            val item = lazyPagingItems[index]
            CharacterItem(person = item as Person)
            Divider()
        }

    }
 */

/*
   when(viewModel.loadingState){
        is AppLoadingState.Loading -> LoadingScreen()
        is AppLoadingState.Error -> ErrorScreen()
        is AppLoadingState.Success -> {

            val lazyPagingItems: LazyPagingItems<ListItem> =
                (viewModel.loadingState as AppLoadingState.Success).data.collectAsLazyPagingItems()

            LazyColumn{

                if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                    item {
                        Text(
                            text = "Паташти шушуть",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                if (lazyPagingItems.loadState.append == LoadState.Loading) {
                    item {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(Alignment.CenterHorizontally)
                        )
                    }
                }

                items(count = lazyPagingItems.itemCount) { index ->
                    val item = lazyPagingItems[index]
                    CharacterItem(person = item as Person)
                    Divider()
                }

            }
        }
    }
 */
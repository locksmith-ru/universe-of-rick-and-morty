package edu.bedaev.universeofrickandmorty.ui.screen.characters

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.AdaptiveScreenContent
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
    val viewModel: CharactersViewModel = viewModel()

    AdaptiveScreenContent(
        modifier = modifier,
        loadingState = viewModel.loadingState,
        adaptiveParams = adaptiveParams,
        currentDestination = Characters,
        onError = { viewModel.loadContent() },
        onTabSelected = { dst ->
            navController.navigateSingleTopTo(dst.route)
        },
        onItemClicked = { item ->
            onItemClicked(item = item)
        }
    )
}

private fun onItemClicked(item: String){
    Log.d(TAG, "onItemClicked: $item")
}
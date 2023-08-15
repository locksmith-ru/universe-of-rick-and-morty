package edu.bedaev.universeofrickandmorty.ui.screen.characters

import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.AdaptiveScreenContent
import edu.bedaev.universeofrickandmorty.ui.components.CharacterItem
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme
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

    AdaptiveScreenContent(
        modifier = modifier,
        loadingState = viewModel.loadingState,
        listItem = { item ->
            CharacterItem(
                person = item as Person,
                onItemClicked = { listItem -> onItemClicked(item = listItem) }
            )
        },
        adaptiveParams = adaptiveParams,
        currentDestination = Characters,
        onError = { viewModel.loadContent() },
        onTabSelected = { dst ->
            navController.navigateSingleTopTo(dst.route)
        }
    )
}

private fun onItemClicked(item: ListItem) {
    Log.d(TAG, "onItemClicked: ${item.id}")
}
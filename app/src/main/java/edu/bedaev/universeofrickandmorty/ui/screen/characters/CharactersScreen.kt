package edu.bedaev.universeofrickandmorty.ui.screen.characters

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.components.AppBottomNavigationBar
import edu.bedaev.universeofrickandmorty.ui.screen.ListScreen

private const val TAG = "_CharactersScreen"

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: CharactersViewModel = viewModel()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        ListScreen(
            modifier = Modifier.weight(1f),
            loadingState = viewModel.loadingState,
            onError = { viewModel.loadContent() },
            onItemSelected = { item -> onItemClicked(item = item) },
        )
        AppBottomNavigationBar(
            modifier = Modifier,
            onTabSelected = { newScreen ->
                navController.navigateSingleTopTo(newScreen.route)
            },
            currentScreen = Characters
        )
    }
}

private fun onItemClicked(item: String){
    Log.d(TAG, "onItemClicked: $item")
}
package edu.bedaev.universeofrickandmorty.ui.screen.episodes

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.bedaev.universeofrickandmorty.navigation.Episodes
import edu.bedaev.universeofrickandmorty.navigation.navigateSingleTopTo
import edu.bedaev.universeofrickandmorty.ui.components.AppBottomNavigationBar
import edu.bedaev.universeofrickandmorty.ui.screen.ListScreen

private const val TAG = "_EpisodesScreen"

@Composable
fun EpisodesScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val viewModel: EpisodeViewModel = viewModel()

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
            currentScreen = Episodes
        )
    }
}

private fun onItemClicked(item: String){
    Log.d(TAG, "onItemClicked: $item")
}
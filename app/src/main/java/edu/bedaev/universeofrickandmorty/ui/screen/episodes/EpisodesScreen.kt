package edu.bedaev.universeofrickandmorty.ui.screen.episodes

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.bedaev.universeofrickandmorty.ui.screen.ListScreen

@Composable
fun EpisodesScreen(
    modifier: Modifier = Modifier,
    onItemSelected: () -> Unit = {}
) {
    val viewModel: EpisodeViewModel = viewModel()

    ListScreen(
        modifier = modifier,
        loadingState = viewModel.loadingState,
        onError = { viewModel.loadContent() },
        onItemSelected = onItemSelected
    )
}
package edu.bedaev.universeofrickandmorty.ui.screen.locations

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.ui.screen.ListScreen
import edu.bedaev.universeofrickandmorty.ui.screen.characters.CharactersViewModel

@Composable
fun LocationsScreen(
    modifier: Modifier = Modifier,
    onItemSelected: () -> Unit = {}
) {
    val viewModel: CharactersViewModel = viewModel()

    ListScreen(
        modifier = modifier,
        screenTitle = stringResource(id = R.string.locations),
        loadingState = viewModel.loadingState,
        onError = { viewModel.loadContent() },
        onItemSelected = onItemSelected
    )
}
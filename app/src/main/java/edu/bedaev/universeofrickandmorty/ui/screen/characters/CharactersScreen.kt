package edu.bedaev.universeofrickandmorty.ui.screen.characters

import android.content.res.Configuration
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.ui.components.AppBottomNavigationBar
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.ErrorScreen
import edu.bedaev.universeofrickandmorty.ui.screen.LoadingScreen
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CharactersScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: CharactersViewModel = viewModel()

    val lazyListState = rememberLazyListState()

    val showFloatingButton by remember {
        derivedStateOf { lazyListState.firstVisibleItemIndex > 0 }
    }

    val scope: CoroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        topBar = {},
        floatingActionButton = {
            if (showFloatingButton) {
                AppFloatingActionButton(
                    onClick = {
                        scope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    }
                )
            }
        },
        bottomBar = {
            AppBottomNavigationBar()
        }
    ) { padding ->
        when (viewModel.loadingState) {
            AppLoadingState.Loading ->  LoadingScreen()
            AppLoadingState.Error ->    ErrorScreen(onRetry = { viewModel::loadCharacters.invoke() })
            is AppLoadingState.Success ->
                ScreenContent(
                    modifier = Modifier.padding(padding),
                    lazyState = lazyListState,
                    data = (viewModel.loadingState as AppLoadingState.Success).data
                )

        }

    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    lazyState: LazyListState,
    data: List<Any>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
        state = lazyState,
    ) {
        item { Header(title = stringResource(id = R.string.characters)) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        items(data) { item ->
            Text(text = item.toString())
        }
    }
}

@Composable
private fun AppFloatingActionButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    // Use `FloatingActionButton` rather than `ExtendedFloatingActionButton` for full control on
    // how it should animate.
    FloatingActionButton(onClick = onClick) {
        Row(
            modifier = modifier.padding(horizontal = 16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowUpward,
                contentDescription = stringResource(id = R.string.to_up)
            )
            Text(
                text = stringResource(R.string.to_up),
                modifier = Modifier
                    .padding(start = 8.dp, top = 3.dp)
            )
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    title: String
) {
    Text(
        text = title,
        modifier = modifier.fillMaxWidth(),
        style = MaterialTheme.typography.titleLarge,
        textAlign = TextAlign.Center
    )
}

@Preview(name = "Light", group = "screens", showBackground = true)
@Preview(
    name = "Dark", group = "screens", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewCharactersScreens() {
    AppTheme {
        CharactersScreen()
    }
}
package edu.bedaev.universeofrickandmorty.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.bedaev.universeofrickandmorty.ui.components.AppFloatingActionButton
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    loadingState: AppLoadingState,
    onError: () -> Unit = {},
    onItemSelected: (String) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    when (loadingState) {
        AppLoadingState.Loading -> LoadingScreen()
        AppLoadingState.Error -> ErrorScreen(onRetry = { onError() })
        is AppLoadingState.Success ->
            ScreenContent(
                modifier = modifier,
                lazyState = lazyListState,
                data = loadingState.data,
                onItemClicked =  onItemSelected
            )
        else -> {}
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    lazyState: LazyListState,
    data: List<Any>,
    onItemClicked: (String) -> Unit
) {
    Box(modifier = modifier) {
        val showFloatingButton by remember {
            derivedStateOf { lazyState.firstVisibleItemIndex > 0 }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 32.dp),
            state = lazyState,
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(data) { item ->
                Text(
                    text = item.toString(),
                    modifier = Modifier.clickable { onItemClicked(item.toString()) }
                )
            }
        }

        if (showFloatingButton) {
            val scope: CoroutineScope = rememberCoroutineScope()
            AppFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .navigationBarsPadding()
                    .padding(bottom = 16.dp, end = 16.dp),
                onClick = {
                    scope.launch { lazyState.animateScrollToItem(0) }
                }
            )
        }

    }
}

@Preview(name = "Light", group = "screens", showBackground = true)
@Preview(
    name = "Dark", group = "screens", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewCharactersScreens() {
    AppTheme {
        ListScreen(
            loadingState =
            AppLoadingState.Success((1..150).toList()
                .map { i -> "Element_${i}" })
        )
    }
}
package edu.bedaev.universeofrickandmorty.ui.screen.characters

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType

private const val TAG = "_CharacterDetailsScreen"

/**
 * Экран с деталями выбранного персонажа
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    title: String? = "",
    characterId: Int? = 0,
    screenParams: Pair<NavigationType, ContentType> =
        Pair(NavigationType.BOTTOM_NAVIGATION, ContentType.LIST_ONLY),
    onBackPressed: () -> Unit = {}
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "go back icon"
                        )
                    }
                },
                title = { Text(text = title ?: "") }
            )
        }
    ) { paddings ->

    }
}

@Preview
@Composable
fun PreviewCharacterDetailsScreen() {
    AppTheme {
        Surface {
            CharacterDetailsScreen(title = "Person details")
        }
    }
}
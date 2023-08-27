package edu.bedaev.universeofrickandmorty.ui.screen.episodes

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.domain.model.Episode
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.ui.components.CharacterItem
import edu.bedaev.universeofrickandmorty.ui.components.HorizontalGradientDivider
import edu.bedaev.universeofrickandmorty.ui.screen.characters.CharactersViewModel
import edu.bedaev.universeofrickandmorty.ui.screen.characters.TextBlock

@Composable
fun EpisodeDetailsScreen(
    modifier: Modifier = Modifier,
    episodeId: Int = 0,
    onBackPressed: () -> Unit = {},
    onItemClicked: (Int) -> Unit = {}
) {
    val viewModel: EpisodeViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.loadMultipleItems(stringList = listOf(episodeId.toString()))
        Log.d("_EpisodeDetailsScreen", "launch with episodeId")
    }

    val episodeList by viewModel.multipleListItemFlow.collectAsState(initial = emptyList())

    if (episodeList.isNotEmpty()) {
        EpisodeDetailsScreen(
            modifier = modifier,
            episode = episodeList.first() as Episode,
            onBackPressed = onBackPressed,
            onCharacterClicked = onItemClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodeDetailsScreen(
    modifier: Modifier = Modifier,
    episode: Episode,
    onBackPressed: () -> Unit = {},
    onCharacterClicked: (Int) -> Unit = {}
) {
    val viewModel: CharactersViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.loadMultipleItems(episode.characters)
        Log.d("_EpisodeDetailsScreen", "launch with object episode")
    }

    val characters by viewModel.multipleListItemFlow.collectAsState(initial = emptyList())

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
                title = {
                    Text(
                        text = "${stringResource(id = R.string.about_title)} ${episode.name}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    ) { paddingValues ->
        val paddingStart = dimensionResource(id = R.dimen.character_detail_start_padding)
        val textColor = MaterialTheme.colorScheme.surface
        val lazyState = rememberLazyListState()
        LazyColumn(
            state = lazyState,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    start = 4.dp, end = 4.dp,
                    bottom = 8.dp
                )
                .background(color = MaterialTheme.colorScheme.inverseSurface)
        ) {
            item {
                HeaderWithDivider(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(top = 48.dp),
                    text = episode.name.uppercase(),
                    textColor = textColor
                )
            }

            item {
                Column(
                    modifier = Modifier.padding(
                        top = dimensionResource(id = R.dimen.vertical_space_between_text_big),
                        start = paddingStart
                    )
                ) {
                    // air date
                    TextBlock(
                        textColor = textColor,
                        subtitle = stringResource(id = R.string.air_date),
                        title = episode.airDate ?: stringResource(id = R.string.unknown)
                    )
                    Divider(modifier = Modifier.padding(end = paddingStart))
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_big)))
                    // episode code
                    TextBlock(
                        textColor = textColor,
                        subtitle = stringResource(id = R.string.episode_code),
                        title = episode.episode ?: stringResource(id = R.string.unknown)
                    )
                    Divider(modifier = Modifier.padding(end = paddingStart))
                }
            }

            item {
                // characters in the episode
                HeaderWithDivider(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_huge),
                            bottom = dimensionResource(id = R.dimen.padding_large)
                        ),
                    textColor = textColor,
                    text = stringResource(id = R.string.characters).uppercase()
                )
            }

            items(characters.size) { index ->
                val item = characters[index]
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                CharacterItem(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                    person = item as Person,
                    imageShape = CircleShape,
                    imageBorderWidth = 4.dp,
                    onItemClicked = { listItem ->
                        onCharacterClicked(listItem.id)
                    }
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                Divider()
            }
        }
    }
}

@Composable
fun HeaderWithDivider(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color,
    dividerHeight: Dp = 2.dp
) {
    Text(
        modifier = modifier,
        text = text,
        color = textColor,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium
    )
    HorizontalGradientDivider(
        modifier = Modifier.padding(
            top = dimensionResource(id = R.dimen.vertical_space_between_text_medium),
            start = dimensionResource(id = R.dimen.padding_medium),
            end = dimensionResource(id = R.dimen.padding_medium)
        ),
        colors = listOf(
            MaterialTheme.colorScheme.inverseSurface,
            MaterialTheme.colorScheme.surface,
            MaterialTheme.colorScheme.inverseSurface
        ),
        height = dividerHeight
    )
}

/*
@Preview(showBackground = true, name = "Light", group = "screens")
@Preview(
    showBackground = true, name = "Dark", group = "screens",
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewEpisodeDetailsScreen() {
    AppTheme {
        Surface {
            EpisodeDetailsScreen(episode = Episode.fakeEpisode())
        }
    }
}*/

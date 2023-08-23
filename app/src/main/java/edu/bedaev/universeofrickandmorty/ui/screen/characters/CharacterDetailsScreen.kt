package edu.bedaev.universeofrickandmorty.ui.screen.characters

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Circle
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.domain.model.Episode
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.ui.components.EpisodeItem
import edu.bedaev.universeofrickandmorty.ui.components.GradientDivider
import edu.bedaev.universeofrickandmorty.ui.screen.episodes.EpisodeViewModel
import edu.bedaev.universeofrickandmorty.ui.utils.GlideImageWithPreview

/**
 * Экран с деталями выбранного персонажа
 */
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    person: Person,
    onBackPressed: () -> Unit = {},
    onEpisodeClicked: (Int) -> Unit
) {

    val viewModel: EpisodeViewModel = hiltViewModel()
    LaunchedEffect(viewModel) {
        viewModel.loadMultipleItems(urlList = person.episodeList)
    }

    val episodes by viewModel.multipleListItemFlow.collectAsState(initial = emptyList())

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
                        text = "${stringResource(id = R.string.about_title)} ${person.name}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    ) { paddings ->

        val paddingStart = dimensionResource(id = R.dimen.character_detail_start_padding)
        val textColor = MaterialTheme.colorScheme.surface
        val lazyState = rememberLazyListState()
        val firstItemTranslationY by remember {
            derivedStateOf {
                when {
                    lazyState.layoutInfo.visibleItemsInfo.isNotEmpty() &&
                            lazyState.firstVisibleItemIndex == 0 ->
                        lazyState.firstVisibleItemScrollOffset * .25f

                    else -> 0f
                }
            }
        }

        val visibility by remember {
            derivedStateOf {
                when {
                    lazyState.layoutInfo.visibleItemsInfo.isNotEmpty() && lazyState.firstVisibleItemIndex == 0 -> {
                        val imageSize = lazyState.layoutInfo.visibleItemsInfo[0].size
                        val scrollOffset = lazyState.firstVisibleItemScrollOffset

                        scrollOffset / imageSize.toFloat()
                    }

                    else -> 1f
                }
            }
        }


        LazyColumn(
            state = lazyState,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddings.calculateTopPadding(),
                    start = 4.dp, end = 4.dp,
                    bottom = 8.dp
                )
                .background(color = MaterialTheme.colorScheme.inverseSurface)
        ) {
            // image
            item {
                GlideImageWithPreview(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(
                            start = 4.dp, end = 4.dp
                        )
                        .graphicsLayer {
                            alpha = 1f - visibility
                            translationY = firstItemTranslationY
                        },
                    contentDescription = "Header image",
                    model = person.image,
                    contentScale = ContentScale.Crop
                )
            }
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = paddingStart),
                    text = person.name.uppercase(),
                    textAlign = TextAlign.Start,
                    color = textColor,
                    style = MaterialTheme.typography.titleLarge,
                )
                GradientDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    dividerHeight = 3.dp
                )
                PersonInfo(
                    modifier = Modifier
                        .padding(
                            start = paddingStart,
                            top = dimensionResource(id = R.dimen.vertical_space_between_text_medium),
                            bottom = dimensionResource(id = R.dimen.vertical_space_between_text_big),
                        )
                        .fillMaxWidth(),
                    textColor = textColor,
                    person = person
                )
            }
            // episodes title
            item {
                Text(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(
                            start = paddingStart,
                            top = dimensionResource(id = R.dimen.vertical_space_between_text_big),
                            bottom = dimensionResource(id = R.dimen.vertical_space_between_text_medium),
                        ),
                    text = "${stringResource(id = R.string.episodes)}:",
                    color = textColor,
                    style = MaterialTheme.typography.titleLarge
                )
                GradientDivider(dividerHeight = 3.dp)
            }
            // episodes items
            items(episodes.size) { index ->
                val episode = episodes[index]
                EpisodeItem(
                    episode = episode as Episode,
                    onItemClicked = { listItem ->
                        onEpisodeClicked(listItem.id)
                    }
                )
                Divider()
            }
        }

    }
}

@Composable
private fun PersonInfo(
    modifier: Modifier = Modifier,
    textColor: Color,
    person: Person
) {
    Column(
        modifier = modifier
    ) {
        // live status
        Status(
            textColor = textColor,
            status = person.status
        )
        GradientDivider(dividerHeight = 1.dp)
        // species and gender
        TextBlock(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.vertical_space_between_text_medium)),
            textColor = textColor,
            subtitle = stringResource(id = R.string.species_gender),
            title = "${person.species}(${person.gender})"
        )
        GradientDivider(dividerHeight = 1.dp)
        // last known location
        TextBlock(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.vertical_space_between_text_medium)),
            textColor = textColor,
            subtitle = stringResource(id = R.string.last_location),
            title = person.location?.name ?: stringResource(id = R.string.unknown)
        )
        GradientDivider(dividerHeight = 1.dp)
        // first seen
        TextBlock(
            modifier = Modifier
                .padding(vertical = dimensionResource(id = R.dimen.vertical_space_between_text_medium)),
            textColor = textColor,
            subtitle = stringResource(id = R.string.first_seen),
            title = person.origin?.name ?: stringResource(id = R.string.unknown)
        )
    }
}

@Composable
private fun TextBlock(
    modifier: Modifier = Modifier,
    textColor: Color,
    subtitle: String,
    title: String
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = subtitle,
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = title,
            color = textColor
        )
    }
}

@Composable
private fun Status(
    modifier: Modifier = Modifier,
    textColor: Color,
    status: String? = edu.bedaev.universeofrickandmorty.domain.Status.UNKNOWN.value
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.life_status),
            color = textColor,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            val statusTitle: String
            val ledColor = when (status?.lowercase()) {
                "alive" -> {
                    statusTitle = stringResource(id = R.string.alive)
                    Color.Green
                }

                "dead" -> {
                    statusTitle = stringResource(id = R.string.dead)
                    Color.Red
                }

                else -> {
                    statusTitle = stringResource(id = R.string.unknown)
                    Color.LightGray
                }
            }
            Icon(
                modifier = Modifier
                    .size(10.dp),
                imageVector = Icons.Filled.Circle,
                contentDescription = "led",
                tint = ledColor
            )
            Text(
                modifier = modifier.padding(8.dp),
                text = statusTitle,
                color = textColor
            )
        }
    }
}
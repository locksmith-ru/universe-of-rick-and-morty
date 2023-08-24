package edu.bedaev.universeofrickandmorty.ui.screen.locations

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.ui.components.CharacterItem
import edu.bedaev.universeofrickandmorty.ui.screen.characters.CharactersViewModel
import edu.bedaev.universeofrickandmorty.ui.screen.characters.TextBlock
import edu.bedaev.universeofrickandmorty.ui.screen.episodes.HeaderWithDivider

@Composable
fun LocationDetailsScreen(
    modifier: Modifier = Modifier,
    locationId: Int = 0,
    onBackPressed: () -> Unit = {},
    onItemClicked: (Int) -> Unit = {}
) {

    val viewModel: LocationViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.loadMultipleItems(stringList = listOf(locationId.toString()))
        Log.d("_LocationDetailsScreen", "launch with locationId")
    }

    val locations by viewModel.multipleListItemFlow.collectAsState(initial = emptyList())

    if (locations.isNotEmpty()) {
        LocationDetailsScreen(
            modifier = modifier,
            location = locations.first() as Location,
            onBackPressed = onBackPressed,
            onItemClicked = onItemClicked
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailsScreen(
    modifier: Modifier = Modifier,
    location: Location,
    onBackPressed: () -> Unit = {},
    onItemClicked: (Int) -> Unit = {}
) {
    val viewModel: CharactersViewModel = hiltViewModel()
    LaunchedEffect(viewModel) {
        viewModel.loadMultipleItems(location.residents)
        Log.d("_LocationDetailsScreen", "launch with object location")
    }
    val residents by viewModel.multipleListItemFlow.collectAsState(initial = emptyList())

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
                        text = "${stringResource(id = R.string.about_title)} ${location.name}",
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
                    text = location.name.uppercase(),
                    textColor = textColor
                )
                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.padding_big))
                )
                //  type
                TextBlock(
                    modifier = Modifier.padding(start = paddingStart),
                    textColor = textColor,
                    subtitle = stringResource(id = R.string.type),
                    title = location.type ?: stringResource(id = R.string.unknown)
                )
                Divider(modifier = Modifier.padding(horizontal = paddingStart))
                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.padding_big))
                )
                // dimension
                TextBlock(
                    modifier = Modifier.padding(start = paddingStart),
                    textColor = textColor,
                    subtitle = stringResource(id = R.string.dimension),
                    title = location.dimension ?: stringResource(id = R.string.unknown)
                )
                Divider(modifier = Modifier.padding(horizontal = paddingStart))
            }

            //resident
            item {
                HeaderWithDivider(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(top = 48.dp),
                    text = stringResource(id = R.string.resident),
                    textColor = textColor
                )
                Spacer(
                    modifier = Modifier
                        .height(dimensionResource(id = R.dimen.padding_big))
                )
            }

            // residents list
            items(residents.size) { index ->
                val item = residents[index]
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
                CharacterItem(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_small)),
                    person = item as Person,
                    imageShape = MaterialTheme.shapes.medium,
                    imageBorderWidth = 2.dp,
                    onItemClicked = { listItem ->
                        onItemClicked(listItem.id)
                    }
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
                Divider()
            }
        }
    }
}

/*
@Preview(showBackground = true, name = "Light", group = "screens")
@Composable
fun PreviewLocationDetails() {
    AppTheme {
        Surface {
            LocationDetailsScreen(location = Location.fakeLocation())
        }
    }
}*/

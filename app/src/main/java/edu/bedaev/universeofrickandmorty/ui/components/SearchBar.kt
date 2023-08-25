package edu.bedaev.universeofrickandmorty.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

enum class SearchWidgetState {
    OPENED, CLOSED
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    title: String = stringResource(id = R.string.app_name),
    hint: String = "",
    searchWidgetState: SearchWidgetState,
    searchTextState: String = "",
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggered: () -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            ClosedBar(
                modifier = modifier,
                title = title,
                onSearchClicked = onSearchTriggered
            )
        }

        SearchWidgetState.OPENED -> {
            OpenedBar(
                text = searchTextState,
                hint = hint,
                onTextChange = onTextChange,
                onCloseClicked = onCloseClicked,
                onSearchClicked = onSearchClicked
            )
        }

        else -> error("unknown search widget state")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClosedBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onSearchClicked: () -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        navigationIcon = {
            Image(
                modifier = Modifier.size(42.dp),
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "navigation icon"
            )
        },
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = { onSearchClicked() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search icon",
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
            }
        }
    )
}

@Composable
fun OpenedBar(
    modifier: Modifier = Modifier,
    text: String = "",
    hint: String = "",
    onTextChange: (String) -> Unit = {},
    onSearchClicked: (String) -> Unit = {},
    onCloseClicked: () -> Unit = {}
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(12.dp)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(alpha = 0.6f),
                    text = hint,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            textStyle = TextStyle(
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(alpha = 0.6f),
                    onClick = { onSearchClicked(text) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search icon",
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.isNotEmpty()) {
                            onTextChange("")
                        } else {
                            onCloseClicked()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.SearchOff,
                        contentDescription = "Close search",
                        tint = MaterialTheme.colorScheme.surfaceTint
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearchClicked(text) }
            ),
            colors = TextFieldDefaults.colors()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClosedBar() {
    AppTheme {
        Surface {
            ClosedBar(
                title = "Characters"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewOpenedBar() {
    AppTheme {
        Surface {
            OpenedBar {

            }
        }
    }
}
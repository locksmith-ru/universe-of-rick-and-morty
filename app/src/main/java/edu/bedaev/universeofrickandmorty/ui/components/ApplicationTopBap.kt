package edu.bedaev.universeofrickandmorty.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplicationTopBar(
    modifier: Modifier = Modifier,
    title: String = ""
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                modifier = Modifier.padding(start = 16.dp)
            )
        },
        navigationIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_default),
                contentDescription = "",
                modifier = Modifier.size(48.dp)
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAppBar() {
    Surface {
        AppTheme {
            ApplicationTopBar(title = "Characters")
        }
    }
}

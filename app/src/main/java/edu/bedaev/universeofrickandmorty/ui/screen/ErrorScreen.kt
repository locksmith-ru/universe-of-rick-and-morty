package edu.bedaev.universeofrickandmorty.ui.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

@Composable
inline fun ErrorScreen(
    modifier: Modifier = Modifier,
    crossinline onRetry: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.loading_failed),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(onClick = { onRetry() }) {
            Text(modifier = Modifier.padding(horizontal = 40.dp),
                text = stringResource(id = R.string.retry))
        }
    }
}

@Preview(name = "Light", group = "screens", showBackground = true)
@Preview(
    name = "Dark", group = "screens", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewErrorScreen() {
    Surface {
        AppTheme {
            ErrorScreen()
        }
    }
}
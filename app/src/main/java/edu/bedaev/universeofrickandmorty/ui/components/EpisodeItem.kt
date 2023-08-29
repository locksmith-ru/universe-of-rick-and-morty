package edu.bedaev.universeofrickandmorty.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.bedaev.universeofrickandmorty.domain.model.Episode
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

@Composable
inline fun EpisodeItem(
    modifier: Modifier = Modifier,
    episode: Episode,
    crossinline onItemClicked: (ListItem) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onItemClicked(episode) },
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.inverseSurface)
        ) {
            val startPad = 16.dp
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .padding(start = startPad)
                        .weight(3f),
                    text = episode.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.surface
                )
                Text(
                    modifier = Modifier
                        .padding(start = startPad)
                        .weight(1f),
                    text = episode.episode.orEmpty(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                        .copy(alpha = 0.5f)
                )
            }
            Text(
                modifier = Modifier.padding(start = startPad),
                text = episode.airDate.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(name = "Light", group = "components", showBackground = true)
@Preview(
    name = "Dark", group = "components", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewEpisodeItem() {
    AppTheme {
        Surface {
            EpisodeItem(episode = Episode.fakeEpisode())
        }
    }
}
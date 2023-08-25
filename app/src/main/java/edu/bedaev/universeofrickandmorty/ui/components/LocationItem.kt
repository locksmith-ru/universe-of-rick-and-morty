package edu.bedaev.universeofrickandmorty.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

@Composable
fun LocationItem(
    modifier: Modifier = Modifier,
    location: Location,
    onItemClicked: (ListItem) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { onItemClicked(location) }
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.inverseSurface)
        ) {
            val startPadding = 24.dp
            Text(
                modifier = Modifier.padding(start = startPadding),
                text = location.name,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                modifier = Modifier.padding(start = startPadding),
                text = location.type.orEmpty(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.surface
            )
            Text(
                modifier = Modifier.padding(start = startPadding),
                text = location.dimension.orEmpty(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surface
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview(name = "Light", group = "components", showBackground = true)
@Preview(name = "Dark", group = "components", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewLocationItem() {
    AppTheme {
        Surface {
            LocationItem(location = Location.fakeLocation())
        }
    }
}
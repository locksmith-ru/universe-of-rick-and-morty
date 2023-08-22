package edu.bedaev.universeofrickandmorty.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme
import edu.bedaev.universeofrickandmorty.ui.utils.GlideImageWithPreview

@Composable
fun CharacterItem(
    modifier: Modifier = Modifier,
    person: Person,
    onItemClicked: (ListItem) -> Unit = {}
) {
    Card(
        modifier = modifier
            .padding(vertical = 4.dp)
            .clickable { onItemClicked(person) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.inverseSurface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImageWithPreview(
                modifier = Modifier.weight(2f),
                model = person.image,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = person.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.surface
                )
                Spacer(modifier = Modifier.size(8.dp))
                AliveSpeciesRow(status = person.status, species = person.species)
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = stringResource(id = R.string.last_location),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.inverseOnSurface
                        .copy(alpha = 0.5f)
                )
                Text(
                    text = person.location?.name.orEmpty(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@Composable
fun AliveSpeciesRow(
    modifier: Modifier = Modifier,
    status: String? = "Alive",
    species: String? = "Human"
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val ledColor = when (status?.lowercase()) {
            "alive" -> Color.Green
            "dead" -> Color.Red
            else -> Color.LightGray
        }
        Icon(
            modifier = Modifier
                .size(10.dp),
            imageVector = Icons.Filled.Circle,
            contentDescription = "led",
            tint = ledColor
        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp),
            text = "$status - $species",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@Preview(
    name = "Dark", group = "components", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(name = "Light", group = "components", showBackground = true)
@Composable
fun PreviewCharacterItem() {
    AppTheme {
        Surface {
            CharacterItem(
                person = Person.fakePerson()
            )
        }
    }
}
package edu.bedaev.universeofrickandmorty.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

@Composable
fun HorizontalGradientDivider(
    modifier: Modifier = Modifier,
    width: Dp = LocalContext.current.resources.displayMetrics.widthPixels.dp,
    height: Dp = 2.dp,
    colors: List<Color> = listOf(MaterialTheme.colorScheme.surface,
        MaterialTheme.colorScheme.inverseSurface),
    tileMode: TileMode = TileMode.Clamp
) {
    Row(modifier = modifier.width(width)) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .background(brush = Brush.horizontalGradient(colors = colors, tileMode = tileMode))
        )
    }
}

@Preview(showBackground = true, name = "Light")
@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL,
    name = "Dark"
)
@Composable
fun PreviewGradientDivider() {
    AppTheme {
        Surface {
            HorizontalGradientDivider(
                width = 250.dp,
                height = 20.dp
            )
        }
    }
}
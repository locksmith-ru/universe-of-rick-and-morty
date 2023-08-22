package edu.bedaev.universeofrickandmorty.ui.components

import android.content.res.Configuration
import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.IntegerRes
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

@Composable
fun GradientDivider(
    modifier: Modifier = Modifier,
    dividerWidth: Dp = LocalContext.current.resources.displayMetrics.widthPixels.dp ,
    dividerHeight: Dp = 2.dp,
    colorFrom: Color = MaterialTheme.colorScheme.surface,
    colorTo: Color = MaterialTheme.colorScheme.inverseSurface
) {
    Row(modifier = modifier.width(dividerWidth)) {
        val gradientColors = listOf(colorFrom, colorTo)
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(dividerHeight)
            .background(brush = Brush.horizontalGradient(colors = gradientColors))
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
            GradientDivider(
                dividerWidth = 150.dp,
                dividerHeight = 10.dp
            )
        }
    }
}
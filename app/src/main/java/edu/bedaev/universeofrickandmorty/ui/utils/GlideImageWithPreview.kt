package edu.bedaev.universeofrickandmorty.ui.utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import edu.bedaev.universeofrickandmorty.R
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GlideImageWithPreview(
    modifier: Modifier = Modifier,
    model: Any? = null,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Fit
) {
    if (model == null) {
        Image(
            modifier = modifier,
            painter = painterResource(id = R.drawable.ic_default),
            contentDescription = contentDescription,
            contentScale = contentScale,
        )
    } else {
        GlideImage(
            modifier = modifier,
            model = model,
            contentDescription = contentDescription,
            contentScale = contentScale
        ) { requestBuilder ->
            requestBuilder
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_broken_image)
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewGlideImage() {
    AppTheme {
        Surface {
            GlideImageWithPreview(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(4.dp, Color.Blue, CircleShape)
            )
        }
    }
}
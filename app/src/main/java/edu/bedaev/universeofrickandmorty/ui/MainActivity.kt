package edu.bedaev.universeofrickandmorty.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.navigation.navTabScreens
import edu.bedaev.universeofrickandmorty.ui.screen.characters.CharactersScreen
import edu.bedaev.universeofrickandmorty.ui.screen.onboarding.OnBoardingScreen
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(tonalElevation = 5.dp) {
                    MainApp()
                }
            }
        }
    }
}

@Composable
fun MainApp(modifier: Modifier = Modifier) {
    var onBoardingIsShown by rememberSaveable { mutableStateOf(false) }

    if (!onBoardingIsShown) {
        OnBoardingScreen(
            modifier = modifier,
            onComplete = { onBoardingIsShown = true }
        )
    } else {
        CharactersScreen()
    }
}

@Preview(
    name = "Dark", showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview(showBackground = true)
@Composable
fun PreviewMainApp() {
    AppTheme {
        Surface {
            MainApp()
        }
    }
}
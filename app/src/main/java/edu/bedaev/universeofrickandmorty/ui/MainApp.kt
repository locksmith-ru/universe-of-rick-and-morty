package edu.bedaev.universeofrickandmorty.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.bedaev.universeofrickandmorty.navigation.AppNavHost
import edu.bedaev.universeofrickandmorty.navigation.Characters
import edu.bedaev.universeofrickandmorty.navigation.navTabScreens
import edu.bedaev.universeofrickandmorty.ui.components.ApplicationTopBar
import edu.bedaev.universeofrickandmorty.ui.screen.onboarding.OnBoardingScreen

@Composable
fun MainApp(modifier: Modifier = Modifier) {
    var onBoardingIsShown by rememberSaveable { mutableStateOf(false) }

    if (!onBoardingIsShown) {
        OnBoardingScreen(
            modifier = modifier,
            onComplete = { onBoardingIsShown = true }
        )
    } else {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen =
            navTabScreens.find { it.route == currentDestination?.route } ?: Characters

        Scaffold(
            topBar = { ApplicationTopBar(title = stringResource(id = currentScreen.titleResId)) },
        ) { padding ->
            AppNavHost(
                modifier = Modifier.padding(padding),
                navController = navController
            )
        }
    }
}
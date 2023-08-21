package edu.bedaev.universeofrickandmorty.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import edu.bedaev.universeofrickandmorty.navigation.AppNavHost
import edu.bedaev.universeofrickandmorty.ui.screen.onboarding.OnBoardingScreen
import edu.bedaev.universeofrickandmorty.ui.utils.ContentType
import edu.bedaev.universeofrickandmorty.ui.utils.DevicePosture
import edu.bedaev.universeofrickandmorty.ui.utils.NavigationType

@Composable
fun MainApp(
    modifier: Modifier = Modifier,
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture
) {
    var onBoardingIsShown by rememberSaveable { mutableStateOf(false) }

    if (!onBoardingIsShown) {
        OnBoardingScreen(
            modifier = modifier,
            onComplete = { onBoardingIsShown = true }
        )
    } else {
        val navController = rememberNavController()

        AppNavHost(
            modifier = Modifier,
            navController = navController,
            adaptiveParams = defineScreenParameters(
                windowSize = windowSize,
                foldingDevicePosture = foldingDevicePosture
            )
        )
    }
}

private fun defineScreenParameters(
    windowSize: WindowWidthSizeClass,
    foldingDevicePosture: DevicePosture
): Pair<NavigationType, ContentType> {
    val navigationType: NavigationType
    val contentType: ContentType
    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ContentType.LIST_AND_DETAIL
            } else {
                ContentType.LIST_ONLY
            }
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                NavigationType.NAVIGATION_RAIL
            } else {
                NavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ContentType.LIST_AND_DETAIL
        }

        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
    }
    return Pair(navigationType, contentType)
}
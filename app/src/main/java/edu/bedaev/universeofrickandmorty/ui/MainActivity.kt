package edu.bedaev.universeofrickandmorty.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.window.layout.FoldingFeature
import androidx.window.layout.WindowInfoTracker
import dagger.hilt.android.AndroidEntryPoint
import edu.bedaev.universeofrickandmorty.ui.theme.AppTheme
import edu.bedaev.universeofrickandmorty.ui.utils.DevicePosture
import edu.bedaev.universeofrickandmorty.ui.utils.isBookPosture
import edu.bedaev.universeofrickandmorty.ui.utils.isSeparating
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val devicePostureFlow = WindowInfoTracker
            .getOrCreate(this)
            .windowLayoutInfo(this)
            .flowWithLifecycle(this.lifecycle)
            .map { layoutInfo ->
                val foldingFeature =
                    layoutInfo.displayFeatures
                        .filterIsInstance<FoldingFeature>()
                        .firstOrNull()
                when {
                    isBookPosture(foldingFeature) ->
                        DevicePosture.BookPosture(foldingFeature.bounds)

                    isSeparating(foldingFeature) ->
                        DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

                    else -> DevicePosture.NormalPosture
                }
            }
            .stateIn(
                scope = lifecycleScope,
                started = SharingStarted.Eagerly,
                initialValue = DevicePosture.NormalPosture
            )

        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(tonalElevation = 5.dp) {
                    val windowSize = calculateWindowSizeClass(this)
                    val devicePosture = devicePostureFlow.collectAsState().value
                    MainApp(
                        windowSize = windowSize.widthSizeClass,
                        foldingDevicePosture = devicePosture
                    )
                }
            }
        }
    }
}
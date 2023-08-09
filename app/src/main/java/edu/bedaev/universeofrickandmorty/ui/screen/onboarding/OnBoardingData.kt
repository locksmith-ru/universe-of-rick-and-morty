package edu.bedaev.universeofrickandmorty.ui.screen.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.res.stringResource
import edu.bedaev.universeofrickandmorty.R

@Immutable
data class OnBoardingData(
    val imageRes: Int,
    val title: String = "",
    val text: String = ""
)

@Composable
fun DefineOnboardingPages(): List<OnBoardingData> {
    return listOf(
        OnBoardingData(
            imageRes = R.drawable.promo,
            title = stringResource(id = R.string.app_name),
            text = "Some text"
        ),
        OnBoardingData(
            imageRes = R.drawable.rick,
            title = "Title 2",
            text = "Some text"
        ),
        OnBoardingData(
            imageRes = R.drawable.what_the_heck,
            title = "Title 1",
            text = "Some text"
        )
    )
}

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
            title = stringResource(id = R.string.what_is_this),
            text = stringResource(id = R.string.about)
        ),
        OnBoardingData(
            imageRes = R.drawable.rick,
            title = stringResource(id = R.string.hundred_locations),
            text = stringResource(id = R.string.access_locations)
        ),
        OnBoardingData(
            imageRes = R.drawable.rick_and_morty,
            title = stringResource(id = R.string.rick_and_morty_api_title),
            text = stringResource(id = R.string.rick_and_morty_api_desc)
        )
    )
}

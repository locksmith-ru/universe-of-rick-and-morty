package edu.bedaev.universeofrickandmorty

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import edu.bedaev.universeofrickandmorty.ui.screen.onboarding.OnBoardingScreen
import org.junit.Rule
import org.junit.Test

class OnboardingScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun onboardingScreen_firstPageTitleDisplayedTest(){
        composeTestRule.setContent {
            OnBoardingScreen(onComplete = { /*TODO*/ })
        }
        composeTestRule
            .onNodeWithContentDescription(context.resources.getString(R.string.what_is_this))
            .assertIsDisplayed()
    }

    @Test
    fun onBoardingScreen_getStartedIsDisplayedTest(){
        composeTestRule.setContent {
            OnBoardingScreen(onComplete = { /*TODO*/ })
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.next))
            .performClick()
            .performClick()

        composeTestRule
            .onNodeWithText(context.resources.getString(R.string.get_started))
            .assertIsDisplayed()
    }

}
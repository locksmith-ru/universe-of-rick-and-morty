package edu.bedaev.universeofrickandmorty.ui.screen.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import edu.bedaev.universeofrickandmorty.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingScreen(
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val items = DefineOnboardingPages()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = items.size,
        infiniteLoop = true
    )

    OnBoardingPager(
        items = items,
        pagerState = pagerState,
        onComplete = { onComplete() },
        modifier = modifier
            .fillMaxSize()
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun OnBoardingPager(
    items: List<OnBoardingData>,
    pagerState: PagerState,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier)
    {
        val scope = rememberCoroutineScope()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            HorizontalPager(state = pagerState) { page ->
                Column(
                    modifier = Modifier
                        .padding(60.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = items[page].imageRes),
                        contentDescription = items[page].title,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = items[page].title,
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier
                            .padding(top = 50.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = items[page].text,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(
                            top = 30.dp, start = 20.dp, end = 20.dp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
            PagerIndicator(size = items.size, currentPage = pagerState.currentPage)
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            BottomPanel(
                currentPage = pagerState.currentPage,
                size = items.size,
                onNext = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            page = pagerState.currentPage + 1,
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessVeryLow
                            )
                        )
                    }
                },
                onComplete = onComplete
            )
        }
    }
}

@Composable
private fun PagerIndicator(
    modifier: Modifier = Modifier,
    size: Int,
    currentPage: Int
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.padding(top = 60.dp)
    ) {
        repeat(times = size) {
            Indicator(isSelected = it == currentPage)
        }
    }
}

@Composable
private fun Indicator(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false
) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 25.dp else 15.dp,
        label = "Pager indicator",
    )
    Box(
        modifier = modifier
            .padding(1.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else
                    Color.Gray.copy(alpha = 0.5f)
            )
    )
}

@Composable
private fun BottomPanel(
    modifier: Modifier = Modifier,
    size: Int,
    currentPage: Int,
    onNext: () -> Unit,
    onComplete: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = if (currentPage != 2) Arrangement.SpaceBetween
        else Arrangement.Center
    ) {
        if (currentPage == size - 1) {
            GetStartedButton(getStartedClicked = { onComplete() })
        } else {
            TextLabel(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(id = R.string.skip),
                onTextPressed = { onComplete() })
            TextLabel(
                modifier = Modifier.padding(end = 20.dp),
                text = stringResource(id = R.string.next),
                onTextPressed = { onNext() })
        }
    }
}

@Composable
private fun GetStartedButton(
    getStartedClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        shape = MaterialTheme.shapes.extraLarge,
        onClick = getStartedClicked,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.get_started),
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 40.dp),
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

@Composable
private fun TextLabel(
    modifier: Modifier = Modifier,
    text: String = "",
    onTextPressed: () -> Unit
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .clickable { onTextPressed() }
    )
}
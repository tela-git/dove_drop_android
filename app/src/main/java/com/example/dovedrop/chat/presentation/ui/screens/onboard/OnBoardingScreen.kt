package com.example.dovedrop.chat.presentation.ui.screens.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dovedrop.R
import com.example.dovedrop.chat.presentation.theme.DoveDropTheme
import com.example.dovedrop.chat.presentation.ui.screens.auth.AuthViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onContinueClick: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val authViewModel: AuthViewModel = koinViewModel()
    val onBoardingImages = listOf(Pair(R.drawable.onboarding_one,R.string.onboarding_one_message), Pair(R.drawable.onboarding_two, R.string.onboarding_two_message))
    val pagerState = rememberPagerState(pageCount = { onBoardingImages.size })
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        if(authViewModel.isUserLoggedIn()) {
            navigateToHome()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dove Drop",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
        )

        Spacer(Modifier.height(40.dp))

        //On boarding images
        OnBoardingHorizontalPager(
            state = pagerState,
            onBoardingImages = onBoardingImages
        )

        Text(
            text = if(pagerState.currentPage == 0)
            stringResource(R.string.onboarding_one_message) else stringResource(R.string.onboarding_two_message),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier,
            textAlign = TextAlign.Center
        )
        Text(
            text = if(pagerState.currentPage == 1) stringResource(R.string.onboarding_two_message_tag) else " ",
            style = MaterialTheme.typography.titleLarge.copy(
                color = MaterialTheme.colorScheme.primary
            )
        )
        Spacer(Modifier.height(30.dp))

        //Horizontal pager position indicator
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .size(8.dp, 8.dp)
                    .background(
                        color = if (pagerState.currentPage == 0) MaterialTheme.colorScheme.onPrimaryContainer else {
                            MaterialTheme.colorScheme.secondaryContainer
                        }
                    )
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .size(8.dp, 8.dp)
                    .background(
                        color = if (pagerState.currentPage == 1) MaterialTheme.colorScheme.onPrimaryContainer else {
                            MaterialTheme.colorScheme.secondaryContainer
                        }
                    )

            )
        }

        Spacer(Modifier.height(60.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(48.dp),
            onClick = {
                if(pagerState.currentPage != 1) {
                    scope.launch { pagerState.animateScrollToPage(1) }
                } else {
                    onContinueClick()
                }
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(
                text = if(pagerState.currentPage == 0) "NEXT" else "CONTINUE",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
private fun OnBoardingHorizontalPager(
    state: PagerState,
    onBoardingImages: List<Pair<Int,Int>>
) {
    HorizontalPager(
       state = state
    ) { page->
        Image(
            painter = painterResource(onBoardingImages[page].first),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f/1.01f)
        )
    }
}

@Preview
@Composable
private fun OnBoardingPreview() {
    DoveDropTheme {
        OnBoardingScreen(
            onContinueClick = {
            },
            navigateToHome = {}
        )
    }
}
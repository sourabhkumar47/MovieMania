package uk.ac.tees.mad.w9617422.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay
import uk.ac.tees.mad.w9617422.R
import uk.ac.tees.mad.w9617422.navUtils.Screen

@Composable
fun SplashScreen(navController: NavController) {

    val logoVisibility = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        logoVisibility.value = true
        delay(3000)
        navController.navigate(Screen.LoginScreen.route) {
            popUpTo(Screen.SplashScreen.route) {
                inclusive = true
            }
        }
    }

    val fadeInAlpha by animateFloatAsState(
        targetValue = if (logoVisibility.value) 1f else 0f,
        animationSpec = tween(durationMillis = 1000), label = ""
    )

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.movie_lottie))
    val progress by animateLottieCompositionAsState(composition)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white)),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = logoVisibility.value,
            enter = fadeInAlphaAnimationSpec(),
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LottieAnimation(
                    composition = composition,
                    progress = progress,
                    modifier = Modifier
                        .alpha(fadeInAlpha)
                )

                Text(
                    text = "Watch all your favourite \n movies and TV shows",
                    modifier = Modifier.alpha(fadeInAlpha),
                    color = colorResource(id = R.color.black),
                    fontSize = 26.sp,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

private fun fadeInAlphaAnimationSpec(): EnterTransition {
    return fadeIn(animationSpec = tween(durationMillis = 1000)) +

            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(durationMillis = 1000)
            )
}
package uk.ac.tees.mad.w9617422.presentation.lottie


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import uk.ac.tees.mad.w9617422.R

@Composable
fun LoadingComponent() {
            LoaderAnimation(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(250.dp),
                anim = R.raw.motionball_loadding
            )
        }

@Composable
fun LoaderAnimation(modifier: Modifier, anim: Int) {

    val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(anim))

    LottieAnimation(
        composition = composition,
        modifier = modifier,
        iterations = LottieConstants.IterateForever
    )

}
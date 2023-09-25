package com.example.emergencyprototype.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import com.example.emergencyprototype.R

@Composable
fun FlickeringBellIcon() {
    val imageVector = painterResource(id = R.drawable.ic_alert_bell_on)
    var alpha by remember { mutableStateOf(1f) }

    val infiniteTransition = rememberInfiniteTransition()
    val alphaAnimation by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    LaunchedEffect(alphaAnimation) {
        alpha = alphaAnimation
    }

    Image(
        painter = imageVector,
        contentDescription = "Flickering Bell Icon",
        modifier = Modifier.alpha(alpha)
    )
}
@Composable
fun BellIcon() {
    val imageVector = painterResource(id = R.drawable.ic_alert_bell_on)
    Image(painter = imageVector, contentDescription = "Bell Icon")
}
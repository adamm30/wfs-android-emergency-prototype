package com.example.emergencyprototype.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergencyprototype.R
import kotlinx.coroutines.delay


@Composable
fun CountdownCircularProgressBar(
    totalTimeMillis: Long,
    onCountdownFinished: () -> Unit
) {
    var remainingTimeMillis by remember { mutableStateOf(totalTimeMillis) }
    var progress by remember { mutableStateOf(1.0f) } // Start with 0% progress

    LaunchedEffect(true) {
        val intervalMillis = 100L // Update the progress every 100 milliseconds

        while (remainingTimeMillis > 0) {

            delay(intervalMillis)
            remainingTimeMillis -= intervalMillis
            progress = 1.0f - (remainingTimeMillis.toFloat() / totalTimeMillis.toFloat())
        }

        // Countdown finished
        onCountdownFinished()
    }

    Box(
        modifier = Modifier
            .size(225.dp),
        contentAlignment = Alignment.Center
    ) {

        ColorChangingCircularProgressIndicator(
            progress = progress,
            modifier = Modifier
                .size(225.dp)
                .fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (remainingTimeMillis / 1000).toString(),
                style = MaterialTheme.typography.headlineLarge,
                fontSize = 70.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
            )
            Text(
                text = stringResource(R.string.seconds_title),
                fontSize = 25.sp,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
@Composable
fun ColorChangingCircularProgressIndicator(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    val startColor = MaterialTheme.colorScheme.surface
    val color = remember { mutableStateOf(startColor) }
    color.value = MaterialTheme.colorScheme.primary

    Canvas(
        modifier = modifier.size(225.dp),
        onDraw = {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2 - 10.dp.toPx()
            val startAngle = 270f // Start angle is at 3 o'clock (counter-clockwise)
            val sweepAngle = -360f * progress // Make the progress counter-clockwise

            // this will make it go clockwise
//            val startAngle = 270f // Start angle is at 12 o'clock
//            val sweepAngle = 360f * progress

            // Draw the gray background circle
            drawCircle(
                color = Color.Gray,
                center = center,
                radius = radius,
                style = Stroke(10.dp.toPx())
            )

            // Draw the progress arc with the changing color
            drawArc(
                color = color.value,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(10.dp.toPx())
            )
        }
    )
}

@Composable
fun CountdownScreen(countdownExpired: () -> Unit) {
    var countdownFinished by remember { mutableStateOf(false) }

    if (countdownFinished) {
        // Countdown finished, show a message or navigate to another screen.
        CountdownCircularProgressBar(
            totalTimeMillis = 0, // 5 seconds countdown
            onCountdownFinished = { countdownFinished = true }
        )
        countdownExpired()
    } else {
        CountdownCircularProgressBar(
            totalTimeMillis = 5000, // 5 seconds countdown
            onCountdownFinished = { countdownFinished = true }
        )
    }
}

package com.example.emergencyprototype.ui.components

import android.os.Handler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emergencyprototype.R
import com.example.emergencyprototype.TimerViewModel
import com.example.emergencyprototype.emergency.EmergencyType
import java.util.concurrent.TimeUnit

@Composable
fun Banner(emergencyType: EmergencyType, onButtonClick: () -> Unit, isCleared: Boolean) {
    val context = LocalContext.current
    val viewModel: TimerViewModel = viewModel()
    var timerExpired by remember { mutableStateOf(false) }
    var buttonPressed by remember { mutableStateOf(false) }
    var isSpinnerVisible by remember { mutableStateOf(false)}
    var escalatingText by remember { mutableStateOf("ESCALATING") }

        DisposableEffect(Unit) {
        viewModel.startTimer(TimeUnit.MINUTES.toMillis(5.toLong())) {
            timerExpired = true
        }
        onDispose {
            viewModel.cancelTimer()
        }
    }
        if(((timerExpired || buttonPressed) && (!isCleared  || (emergencyType == EmergencyType.Monitored)))  ) {
//            if (emergencyType == EmergencyType.Monitored) {


                // Schedule switching to HelpRequested after 5 seconds
                Handler().postDelayed({
                    escalatingText =  if (emergencyType == EmergencyType.Monitored) "911 has been contacted" else context.getString(R.string.tier_two_notified)
                    isSpinnerVisible = false
                }, 3000) // 5000 milliseconds = 5 seconds
//            }
        }

    //    if(((timerExpired || buttonPressed) && (!isCleared  || (emergencyType == EmergencyType.Monitored)))  ) {
//        if(emergencyType == EmergencyType.Monitored){

    val bannerColor = when {
        (timerExpired || buttonPressed) && (!isCleared || (emergencyType == EmergencyType.Monitored)) -> {
            if (emergencyType == EmergencyType.Monitored) {
                Handler().postDelayed({
                    buttonPressed = true
                }, 5000) // 5000 milliseconds = 5 seconds
                Color.Red
            } else {
                Color.Red
            }
        }
        isCleared -> {
            colorResource(id = R.color.success_green)
        }
        else -> {
            MaterialTheme.colorScheme.primary
        }
    }
    Surface(
        color = bannerColor,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .heightIn(min = 56.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val text = when {
                (timerExpired || buttonPressed) && emergencyType == EmergencyType.Monitored -> escalatingText // "911 has been contacted"
                (timerExpired || buttonPressed) && !isCleared ->  escalatingText//context.getString(R.string.tier_two_notified)
                isCleared -> context.getString(R.string.tier_two_cleared)
                else -> context.getString(R.string.tier_2_escalation_text, viewModel.timerText)
            }
            Row(
                modifier = Modifier.weight(1f), // This allows the text to take up as much space as possible
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.titleMedium,
                    color = if (isCleared) Color.Black else MaterialTheme.colorScheme.onBackground,
                    maxLines = 2, // Adjust as needed
                    overflow = TextOverflow.Ellipsis // Add ellipsis (...) for overflow
                )
                if (isSpinnerVisible) {
                    Spacer(modifier = Modifier.width(8.dp))
                    CircularProgressIndicator(
                        modifier = Modifier.size(32.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }


            if (!isCleared && !timerExpired && !buttonPressed) {
//                Spacer(modifier = Modifier.width(16.dp))
                Row(
                    modifier = Modifier.weight(.5f), // This allows the text to take up as much space as possible
                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            onButtonClick()
                            buttonPressed = true
                            isSpinnerVisible = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.escalate),
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }

//    Surface(
//        color = bannerColor,
//        modifier = Modifier
//            .fillMaxWidth()
//            .wrapContentHeight()
//            .heightIn(min = 56.dp)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center //remove this for spacing in the middle
//        ) {
//            val text = when {
//                (timerExpired || buttonPressed) && emergencyType == EmergencyType.Monitored -> escalatingText // "911 has been contacted"
//                (timerExpired || buttonPressed) && !isCleared ->  escalatingText//context.getString(R.string.tier_two_notified)
//                isCleared -> context.getString(R.string.tier_two_cleared)
//                else -> context.getString(R.string.tier_2_escalation_text, viewModel.timerText)
//            }
//            Text(
//                text = text,
//                style = MaterialTheme.typography.titleMedium,
//                color = if (isCleared) Color.Black else MaterialTheme.colorScheme.onBackground
//            )
//            if (isSpinnerVisible) {
//                Spacer(modifier = Modifier.width(8.dp))
//                CircularProgressIndicator(
//                    modifier = Modifier.size(32.dp),
//                    color = MaterialTheme.colorScheme.onBackground
//                )
//            }
//
//            if (!isCleared && !timerExpired && !buttonPressed) {
//                Spacer(modifier = Modifier.width(16.dp))
//
//                Button(
//                    onClick = {
//                        onButtonClick()
//                        buttonPressed = true
//                        isSpinnerVisible = true
//                    },
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Red
//                    ),
//                    shape = RoundedCornerShape(14.dp)
//                ) {
//                    Text(
//                        text = stringResource(R.string.escalate),
//                        fontSize = 18.sp,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//
//            }
//        }
//    }
}

//@Composable
//fun Banner(emergencyType: EmergencyType, onButtonClick: () -> Unit, isCleared: Boolean) {
//    val context = LocalContext.current
//    val viewModel: TimerViewModel = viewModel()
//    var timerExpired by remember { mutableStateOf(false) }
//    var buttonPressed by remember { mutableStateOf(false) }
//    // Start the countdown timer when the composable is first displayed
//    DisposableEffect(Unit) {
//        viewModel.startTimer(TimeUnit.MINUTES.toMillis(5.toLong())) {
//            timerExpired = true
//        }
//        onDispose {
//            viewModel.cancelTimer()
//        }
//    }
//    if(((timerExpired || buttonPressed) && (!isCleared  || (emergencyType == EmergencyType.Monitored)))  ) {
//        if(emergencyType == EmergencyType.Monitored){
//            var text by remember { mutableStateOf("Escalating") }
//
//            // Schedule switching to HelpRequested after 5 seconds
//            Handler().postDelayed({
//                text = "911 has been contacted"
//            }, 5000) // 5000 milliseconds = 5 seconds
//            Surface(
//                color = Color.Red,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//                    .heightIn(min = 56.dp)
////                    .height(56.dp)
//            ) {
//
//                Column(
//                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
////                    modifier = Modifier
////                        .padding(start = 12.dp),
//                        //.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                ) {
//                    Text(
//                        text = text,
//                        style = MaterialTheme.typography.titleMedium,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//            }
//        } else {
//            Surface(
//                color = Color.Red,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//                    .heightIn(min = 56.dp)
////                    .height(56.dp)
//            ) {
//
//                Column(
//                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
////                    modifier = Modifier
////                        .padding(start = 12.dp),
//                        //.fillMaxSize(),
//                    horizontalAlignment = Alignment.CenterHorizontally,
//                    verticalArrangement = Arrangement.Center,
//                ) {
//                    Text(
//                        text = stringResource(R.string.tier_two_notified),
//                        style = MaterialTheme.typography.titleMedium,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//            }
//        }
//
//    } else if(isCleared){
//        Surface(
//            color = colorResource(id = R.color.success_green),
//            modifier = Modifier
//                .wrapContentHeight()
//                .heightIn(min = 56.dp)
//                .fillMaxWidth()
////                .height(56.dp)
//        ) {
//
//            Column(
//                modifier = Modifier.padding(top = 12.dp, bottom = 12.dp),
////                modifier = Modifier
////                    .padding(start = 12.dp),
//                    //.fillMaxSize(),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center,
//            ) {
//                Text(
//                    text = stringResource(R.string.tier_two_cleared),
//                    style = MaterialTheme.typography.titleMedium,
//                    color = Color.Black
//                )
//            }
//        }
//    }
//
//    else {
//        Surface(
//            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//
//        ) {
//
//            Row(
//                modifier = Modifier.fillMaxWidth().heightIn(min = 56.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.Center //remove this for spacing in the middle
//            ) {
//                Column(
//                    modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
//                ) {
//                    Text(
//                        text = context.getString(
//                            R.string.tier_2_escalation_text,
//                            viewModel.timerText
//                        ),
//                        style = MaterialTheme.typography.titleMedium,
//                        color = MaterialTheme.colorScheme.onBackground
//                    )
//                }
//                Spacer(modifier = Modifier.width(16.dp))
//                    Button(
//                        onClick = {
//                            onButtonClick()
//                            buttonPressed = true
//                        },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Red
//                        ),
//                        shape = RoundedCornerShape(14.dp)
//                    ) {
//                        Text(
//                            text = stringResource(R.string.escalate),
//                            fontSize = 18.sp,
//                            color = MaterialTheme.colorScheme.onBackground
//                        )
//                    }
//
//            }
//        }
//    }
//}



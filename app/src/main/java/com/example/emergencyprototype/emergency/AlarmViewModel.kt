package com.example.emergencyprototype.emergency

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emergencyprototype.R
import kotlinx.coroutines.delay


@Composable
fun AlarmUI() {
    val context = LocalContext.current
    val viewModel: EmergencyViewModel = viewModel()
    var isSoundOn by remember { mutableStateOf(true) }

    // Schedule switching to HelpRequested after 2 seconds
    LaunchedEffect(Unit) {
        delay(2000) // 2000 milliseconds = 2 seconds
        viewModel.isEmergencyActive = true
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(top = 72.dp)
            .fillMaxSize()
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(56.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(onClick = { isSoundOn = !isSoundOn })
            ) {

                if (isSoundOn) {
//                    FlickeringBellIcon()
                    Image(
                        painter = painterResource(id = R.drawable.ic_alert_bell_on),
                        contentDescription = stringResource(R.string.HelpRequestedBellAlertIconDescription),
                        //modifier = Modifier.size(159.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }
                else{
                    Image(
                        painter = painterResource(id = R.drawable.ic_alert_bell_off),
                        contentDescription = stringResource(R.string.HelpRequestedBellAlertIconDescription),
                        //modifier = Modifier.size(159.dp),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                    )
                }

                val bellIcon = if (isSoundOn) R.drawable.ic_alert_bell_on else R.drawable.ic_alert_bell_off

//                Image(
//                    painter = painterResource(id = bellIcon),
//                    contentDescription = stringResource(R.string.HelpRequestedBellAlertIconDescription),
//                    //modifier = Modifier.size(159.dp),
//                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
//                )
                Text(
                    text = stringResource(
                        if (isSoundOn) R.string.help_requested_alarm_sound_on else R.string.help_requested_alarm_sound_off
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 16.dp),
                    style = MaterialTheme.typography.titleMedium,

                )

            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.help_requested_title),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
                if (!viewModel.isEmergencyActive) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(62.dp)
                    )
                } else {
                    Text(
                        text = context.getString(R.string.help_requested_body, "Dillon Batt"),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (viewModel.isEmergencyActive) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            viewModel.showAllClearDialog = true
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.80f)
                            .padding(8.dp).border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(14.dp)),
//                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.background,

                            )
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = stringResource(id = R.string.send_all_clear_title),
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

//                    ExtendedFloatingActionButton(
//                        onClick = {
//                            viewModel.showAllClearDialog = true
//                            // navController.navigate("clearEmergencyFragment")
//                            // You can call the corresponding function or action here.
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth(0.70f)
//                            .padding(8.dp),
//                        containerColor = MaterialTheme.colorScheme.background
//                    ) {
//                        Text(
//                            text = stringResource(id = R.string.send_all_clear_title),
//                            fontSize = 18.sp,
//                            color = MaterialTheme.colorScheme.primary
//                        )
//                    }
                }
            }
        }
    }
}
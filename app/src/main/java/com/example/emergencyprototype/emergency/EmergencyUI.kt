package com.example.emergencyprototype.emergency

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emergencyprototype.MainActivity
import com.example.emergencyprototype.R
import com.example.emergencyprototype.ui.components.Banner
import com.example.emergencyprototype.ui.components.MultiLineText
import com.example.emergencyprototype.ui.components.TwoButtonDialog
import com.example.emergencyprototype.ui.components.rememberImeState

@Composable
fun EmergencyUI(emergencyType: EmergencyType) {
    val viewModel: EmergencyViewModel = viewModel()
    val titleText = if (viewModel.initiateAllClear)
        stringResource(id = R.string.all_clear_reason_title) else stringResource(id = R.string.help_alert_title)


    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = titleText,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        val showBanner =
            (!viewModel.confirmAllClear &&
                    (emergencyType == EmergencyType.Tiered || emergencyType == EmergencyType.Monitored))
            ||
            (viewModel.confirmAllClear && (emergencyType == EmergencyType.Monitored && viewModel.isEmergencyEscalated))

        if (showBanner && viewModel.isEmergencyActive) {
            Banner(
                emergencyType,
                onButtonClick = { viewModel.isEmergencyEscalated = true },
                isCleared = viewModel.initiateAllClear
            )
        } else {
            val spacerHeight = if (viewModel.initiateAllClear && emergencyType == EmergencyType.Normal) {
                0.dp
            } else {
                56.dp
            }
            Spacer(modifier = Modifier.height(spacerHeight))
        }

        if (viewModel.initiateAllClear) {
            val imeState = rememberImeState()
            val scrollState = rememberScrollState()
            LaunchedEffect(key1 = imeState.value) {
                if (imeState.value) {
                    scrollState.scrollTo((scrollState.maxValue))
                }
            }
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .verticalScroll(scrollState)
                    .fillMaxSize()
            )
            {
                ClearEmergency()
            }
        } else if (viewModel.confirmAllClear) {
            val scroll = rememberScrollState(0)
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .verticalScroll(scroll)
                    .fillMaxSize()
            )
            {
                ConfirmAllClear(emergencyType)
            }
        } else {
            val scroll = rememberScrollState(0)
            Column(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
                    .verticalScroll(scroll)
                    .fillMaxSize()
            )
            {
                AlarmUI()
            }
        }

        if (viewModel.showAllClearDialog) {

            TwoButtonDialog(
                title = stringResource(id = R.string.confirm_all_clear_dialog_title),
                body = stringResource(id = R.string.confirm_all_clear_dialog_body),
                onConfirm = {
                    viewModel.onAllClearPressed()
//                    viewModel.isAllClearPressed = true
//                    viewModel.showAllClearDialog = false
                },
                onDismiss = { viewModel.showAllClearDialog = false }
            )
        }
    }
}

@Composable
fun ClearEmergency() {
    val radioOptions = listOf("False Alarm", "Just Testing", "Emergency Resolved")
    val viewModel: EmergencyViewModel = viewModel()
    var (selectedOption, onOptionSelected) = remember { mutableStateOf<String?>(null) }
    var isButtonEnabled by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
    ) {
        Column(

            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier
                    .selectableGroup()
                    .padding(start = 16.dp)) {
                radioOptions.forEach { text ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .selectable(
                                selected = (text == selectedOption),
                                onClick = {
                                    onOptionSelected(text)
                                    isButtonEnabled = true
                                },
                                role = Role.RadioButton
                            )
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = null // null recommended for accessibility with screenreaders
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            MultiLineText()
            Button(
                onClick = {
                    viewModel.onAllClearConfirmedPressed()
//                    viewModel.isAllClearConfirmedPressed = true
//                    viewModel.isAllClearPressed = false
                },
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .padding(8.dp),
                shape = RoundedCornerShape(14.dp),
                enabled = isButtonEnabled, // Enable the button based on the state
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = stringResource(id = R.string.send_all_clear_button),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}


@Composable
fun ConfirmAllClear(emergencyType: EmergencyType) {
    val context = LocalContext.current
    val viewModel: EmergencyViewModel = viewModel()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
            //.padding(top = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_all_clear_sent),
            contentDescription = stringResource(R.string.AllClearAlertIconDescription),
            modifier = Modifier.size(159.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(id = R.string.all_clear_sent_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
        if (viewModel.isEmergencyEscalated && emergencyType == EmergencyType.Monitored) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.call_operator_body),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "+1 (801) 781-6101",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.height(56.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            if (viewModel.isEmergencyEscalated && emergencyType == EmergencyType.Monitored) {

                ExtendedFloatingActionButton(
                    onClick = {
                        // Implement the action when "Call Now" is clicked
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.90f)
                        .padding(8.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = stringResource(id = R.string.call_now_button),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.verbally_cleared_button),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        restartApp(context)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Restart Simulation",
                color = MaterialTheme.colorScheme.primary,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable {
                    restartApp(context)
                }
            )
        }
    }
}

fun restartApp(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    context.startActivity(intent)

}
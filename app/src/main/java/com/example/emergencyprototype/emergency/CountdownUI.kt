package com.example.emergencyprototype.emergency

import androidx.activity.OnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergencyprototype.R
import com.example.emergencyprototype.ui.components.CountdownScreen

@Composable
fun CountdownUi(onCountdownExpired: () -> Unit){
    val context = LocalContext.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()

    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Sending alerts to",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "Dillon Batt in....",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.titleLarge,
                )
            }

            Box(
                contentAlignment = Alignment.Center
            )
            {
                CountdownScreen(onCountdownExpired)
            }
            Button(
                onClick = {
                    (context as? OnBackPressedDispatcherOwner)?.onBackPressedDispatcher?.onBackPressed()
                },
                modifier = Modifier
                    .fillMaxWidth(0.80f)
                    .padding(8.dp).border(width = 2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(14.dp)),
//                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,

                )
            ) {
                Text(
                    modifier = Modifier
                        .padding(8.dp),
                    text = stringResource(id = R.string.im_okay_button),
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

//            ExtendedFloatingActionButton(
//                onClick = {
//                    (context as? OnBackPressedDispatcherOwner)?.onBackPressedDispatcher?.onBackPressed()
//                },
//                modifier = Modifier
//                    .fillMaxWidth(0.70f)
//                    .padding(8.dp),
//                containerColor = MaterialTheme.colorScheme.background,
//            ) {
//                Text(
//                    text = stringResource(id = R.string.im_okay_button),
//                    fontSize = 18.sp,
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }
        }
    }
}
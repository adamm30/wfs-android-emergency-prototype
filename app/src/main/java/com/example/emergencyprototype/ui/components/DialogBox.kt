package com.example.emergencyprototype.ui.components


import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.emergencyprototype.R
import com.example.emergencyprototype.emergency.EmergencyViewModel

@Composable
fun TwoButtonDialog(title: String, body: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    val viewModel: EmergencyViewModel = viewModel()
    AlertDialog(
        onDismissRequest = {

        },
        title = {
            Text(
                title,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        // Cancel Button
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.dismiss_dialog_option),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
            }
        },
        // Delete Button
        dismissButton = {

            Button(
                onClick = {
                    Log.d("Adam", "In Onclick")
                    onConfirm()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 1.dp)
                    .offset(y = 15.dp)
            ) {
                Text(text = stringResource(R.string.confirmation_dialog_option), color = Color.White, fontSize = 16.sp)
            }
        },
        text = {
            Text(body, fontSize = 16.sp)
        }
    )
}

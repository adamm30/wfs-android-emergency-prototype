package com.example.emergencyprototype.ui.components


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergencyprototype.R

@Composable
fun TwoButtonDialog(title: String, body: String, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        containerColor = colorResource(id = R.color.dialog_gray),
        onDismissRequest = {

        },
        title = {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                color = Color.White
            )
        },
        // Cancel Button
        confirmButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.dialog_gray)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp),
                    text = stringResource(R.string.cancel_dialog_option),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 16.sp
                )
            }
        },
        // Delete Button
        dismissButton = {
            Button(
                onClick = {
                    onConfirm()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .offset(y = 15.dp),
                shape = RoundedCornerShape(14.dp),
                border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = stringResource(R.string.yes_dialog_option),
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
            }
        },
        text = {
            Text(
                body,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                textAlign = TextAlign.Center)
        }
    )
}

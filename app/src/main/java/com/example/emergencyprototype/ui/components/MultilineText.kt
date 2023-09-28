package com.example.emergencyprototype.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergencyprototype.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MultiLineText(){

    var text by remember { mutableStateOf(TextFieldValue("")) }
    Box(
        modifier = Modifier.fillMaxWidth(),
    ) {
        OutlinedTextField(
            singleLine = false,
            value = text,
            onValueChange = { newText: TextFieldValue ->
                if (newText.text.length <= 150) {
                    text = newText
                }
            },
            placeholder = {
                Text(
                    stringResource(R.string.placeholder_notes),
                    color = Color.LightGray.copy(alpha = 0.6f)
                )
            },

            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .heightIn(150.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor =  MaterialTheme.colorScheme.onBackground,
                cursorColor = MaterialTheme.colorScheme.onBackground,
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onBackground,
                containerColor = colorResource(id = R.color.multiline_text_field_gray)
            ),
            textStyle = TextStyle(fontSize = 18.sp),
            maxLines = 6
        )
        // Display character count
        Text(
            text = "${text.text.length}/150",
            color = if (text.text.length > 150) Color.Red else Color.Gray,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(start = 24.dp, end = 24.dp)
        )
    }
}

package com.example.emergencyprototype

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emergencyprototype.StaticValues.EMERGENCY_TYPE
import com.example.emergencyprototype.emergency.EmergencyActivity
import com.example.emergencyprototype.emergency.EmergencyType
import com.example.emergencyprototype.ui.theme.EmergencyPrototypeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeMenu(this)
        }
    }
}



@Composable
fun HomeMenu(context: Context){
    EmergencyPrototypeTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), // Add some padding for spacing
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExtendedFloatingActionButton(
                    onClick = {
                        val intent: Intent = Intent(context, EmergencyActivity::class.java)
                        intent.putExtra(
                            EMERGENCY_TYPE,
                            EmergencyType.Normal.value
                        )
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.90f)
                        .padding(8.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "Normal",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                ExtendedFloatingActionButton(
                    onClick = {
                        val intent: Intent = Intent(context, EmergencyActivity::class.java)
                        intent.putExtra(
                            EMERGENCY_TYPE,
                            EmergencyType.Tiered.value
                        )
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.90f)
                        .padding(8.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "Tiered Alerts",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
                ExtendedFloatingActionButton(
                    onClick = {
                        val intent: Intent = Intent(context, EmergencyActivity::class.java)
                        intent.putExtra(
                            EMERGENCY_TYPE,
                            EmergencyType.Monitored.value
                        )
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.90f)
                        .padding(8.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "Monitored",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

package com.example.emergencyprototype.emergency

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.emergencyprototype.StaticValues
import com.example.emergencyprototype.StaticValues.EMERGENCY_TYPE
import com.example.emergencyprototype.ui.theme.EmergencyPrototypeTheme

class EmergencyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val emergencyType =  intent.getStringExtra(EMERGENCY_TYPE)

        val emergencyTypeValue =  intent.getStringExtra(StaticValues.EMERGENCY_TYPE)
        val emergencyTypeTwo: EmergencyType = EmergencyType.values().find { it.value == emergencyTypeValue } ?: EmergencyType.Normal


        setContent {
            EmergencyPrototypeTheme {
                Column (modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()){
                    AppNavigation(emergencyTypeTwo, emergencyType )
                }
            }
        }
    }

    fun requestHelp(navController: NavController){
        navController.navigate("emergencyFragment")
    }

    @Composable
    fun AppNavigation(emergencyType: EmergencyType, emergencyTypeTwo: String?) {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "countdownFragment") {
            composable("countdownFragment") {
                CountdownUi { requestHelp(navController) }
            }
            composable("emergencyFragment") {
                EmergencyUI(emergencyType)
            }
        }
    }


}



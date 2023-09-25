package com.example.emergencyprototype.emergency

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class EmergencyViewModel : ViewModel() {

    sealed class State {
        object Idle : State()
        object AllClearInitiated : State()
        object AllClearConfirmed : State()
    }

    //Default
    private var currentState by mutableStateOf<State>(State.Idle)

    var isEmergencyActive by mutableStateOf(false)
    var initiateAllClear by mutableStateOf(false)
    var confirmAllClear by mutableStateOf(false)
    var isEmergencyEscalated by mutableStateOf(false)
    var showAllClearDialog by mutableStateOf(false)


    //Change states
    private fun transitionTo(newState: State) {
        currentState = newState

        when (newState) {
            is State.AllClearConfirmed -> {
                confirmAllClear = true
                initiateAllClear = false
            }
            is State.AllClearInitiated -> {
                initiateAllClear = true
                showAllClearDialog = false
            }
            else -> { }
        }
    }

    fun onAllClearPressed() {
        if (currentState != State.AllClearInitiated) {
            transitionTo(State.AllClearInitiated)
        }
    }

    fun onAllClearConfirmedPressed() {
        if (currentState != State.AllClearConfirmed) {
            transitionTo(State.AllClearConfirmed)
        }
    }

}

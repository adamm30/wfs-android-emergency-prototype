package com.example.emergencyprototype

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

class TimerViewModel : ViewModel() {
    private val countdownInterval = 1000L // 1 second interval

    private var timer: CountDownTimer? = null

    private val _timerText = mutableStateOf("00:00")
    val timerText: String
        get() = _timerText.value

    fun startTimer(initialTimeMillis: Long, onTimerExpired: () -> Unit) {
        timer = object : CountDownTimer(initialTimeMillis, countdownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished).toString().padStart(2, '0')
                val seconds = (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60).toString().padStart(2, '0')
                _timerText.value = "$minutes:$seconds"
            }

            override fun onFinish() {
                _timerText.value = "00:00"
                // Execute the provided function when the timer expires
                onTimerExpired()
            }
        }.start()
    }

    fun cancelTimer() {
        timer?.cancel()
    }
}
package com.example.stopwatch.main

import androidx.annotation.StringRes

interface MainView {
    fun updateStartButtonText(@StringRes textRes: Int)
    fun updateResetButtonVisibility(visible: Boolean)
    fun updateTimeText(text: String)
}
package com.example.stopwatch.main

import com.example.stopwatch.R
import com.example.stopwatch.utils.OnTimeUpdate

class MainPresenter : OnTimeUpdate {

    private var view: MainView? = null

    // Data
    private var started: Boolean = false
    private var startTime: Long = 0L
    private var endTime: Long = 0L
    private var accumulatedTime: Long = 0L

    fun attach(view: MainView) {
        this.view = view
    }

    // Actions
    fun toggleStart(currentTimeMillis: Long) {
        started = !started
        val newLabelRes = if (started) R.string.label_stop else R.string.label_start
        view?.updateStartButtonText(newLabelRes)
        if (started) {
            startTime = currentTimeMillis
            view?.updateResetButtonVisibility(true)
        } else {
            endTime = currentTimeMillis
            accumulatedTime += endTime - startTime
        }
    }

    fun reset(currentTimeMillis: Long) {
        if (started) {
            toggleStart(currentTimeMillis)
        }
        startTime = endTime
        accumulatedTime = 0
        updateTimeText()

        view?.updateResetButtonVisibility(false)
    }

    override fun onUpdate(delay: Long, currentTimeMillis: Long) {
        if (started) {
            endTime = currentTimeMillis
            updateTimeText()
        }
    }

    internal fun updateTimeText() {
        val fullMillis = accumulatedTime + endTime - startTime
        val millis = fullMillis % 1000
        val seconds = (fullMillis / 1000) % 60
        val minutes = (fullMillis / 1000 / 60) % 60

        view?.updateTimeText(String.format("%02d:%02d.%03d", minutes, seconds, millis))
    }
}
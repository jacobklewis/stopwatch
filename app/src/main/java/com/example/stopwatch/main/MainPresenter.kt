package com.example.stopwatch.main

import android.os.Bundle
import com.example.stopwatch.R
import com.example.stopwatch.utils.OnTimeUpdate

class MainPresenter : OnTimeUpdate {

    private var view: MainView? = null

    // Data
    private var started: Boolean = false
    private var startTime: Long = 0L
    private var endTime: Long = 0L
    private var accumulatedTime: Long = 0L

    private val totalElapsedTime: Long
        get() = accumulatedTime + endTime - startTime

    fun attach(view: MainView) {
        this.view = view
    }

    fun saveState(outState: Bundle?) {
        outState?.putBoolean("started", started)
        outState?.putLong("startTime", startTime)
        outState?.putLong("endTime", endTime)
        outState?.putLong("accumulatedTime", accumulatedTime)
    }

    fun restore(savedState: Bundle?) {
        savedState ?: return

        started = savedState.getBoolean("started", false)
        startTime = savedState.getLong("startTime", 0L)
        endTime = savedState.getLong("endTime", 0L)
        accumulatedTime = savedState.getLong("accumulatedTime", 0L)
        updateStartUI()
    }

    // Actions
    fun toggleStart(currentTimeMillis: Long) {
        started = !started
        updateStartUI()
        if (started) {
            accumulatedTime += endTime - startTime
            startTime = currentTimeMillis
        } else {
            endTime = currentTimeMillis
        }
    }

    private fun updateStartUI() {
        val newLabelRes = if (started) R.string.label_stop else R.string.label_start
        view?.updateStartButtonText(newLabelRes)
        if (started || totalElapsedTime > 0) {
            view?.updateResetButtonVisibility(true)
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
        val fullMillis = totalElapsedTime
        val millis = fullMillis % 1000
        val seconds = (fullMillis / 1000) % 60
        val minutes = (fullMillis / 1000 / 60) % 60

        view?.updateTimeText(String.format("%02d:%02d.%03d", minutes, seconds, millis))
    }

}
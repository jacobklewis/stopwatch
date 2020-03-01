package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var started: Boolean = false
    private var startTime: Long = 0L
    private var endTime: Long = 0L
    private var accumulatedTime: Long = 0L
    private val uiHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_start_toggle.setOnClickListener(this)
        button_reset.setOnClickListener(this)

        updateTimeText()

        fixedRateTimer("stopwatch", period = 50) {
            if (started) {
                uiHandler.post {
                    endTime = System.currentTimeMillis()
                    updateTimeText()
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.button_start_toggle -> handleStartToggle()
            R.id.button_reset -> handleResetToggle()
        }
    }

    private fun handleStartToggle() {
        started = !started
        val newLabelRes = if (started) R.string.label_stop else R.string.label_start
        button_start_toggle.setText(newLabelRes)
        if (started) {
            startTime = System.currentTimeMillis()
            button_reset.visibility = View.VISIBLE
        } else {
            endTime = System.currentTimeMillis()
            accumulatedTime += endTime - startTime
        }
    }

    private fun handleResetToggle() {
        if (started) {
            handleStartToggle()
        }
        startTime = endTime
        accumulatedTime = 0
        updateTimeText()

        button_reset.visibility = View.GONE
    }

    private fun updateTimeText() {
        val fullMillis = accumulatedTime + endTime - startTime
        val millis = fullMillis % 1000
        val seconds = (fullMillis / 1000) % 60
        val minutes = (fullMillis / 1000 / 60) % 60

        text_time.text = String.format("%02d:%02d.%03d", minutes, seconds, millis)
    }

}

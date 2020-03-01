package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var started: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_start_toggle.setOnClickListener(this)
        button_reset.setOnClickListener(this)
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
    }

    private fun handleResetToggle() {
        if (started) {
            handleStartToggle()
        }
    }
}

package com.example.stopwatch.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.stopwatch.R
import com.example.stopwatch.utils.OnTimeUpdate
import com.example.stopwatch.utils.setInterval
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView, View.OnClickListener {

    private val presenter = MainPresenter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Attach UI Click Events
        button_start_toggle.setOnClickListener(this)
        button_reset.setOnClickListener(this)

        // Attach Interval and Presenter
        presenter.attach(this)
        setInterval(this, presenter, 50)

        // Zero out Display
        presenter.updateTimeText()
    }

    override fun onClick(v: View?) {
        val millisTime = System.currentTimeMillis()
        when(v?.id) {
            R.id.button_start_toggle -> presenter.toggleStart(millisTime)
            R.id.button_reset -> presenter.reset(millisTime)
        }
    }

    override fun updateStartButtonText(textRes: Int) {
        button_start_toggle.setText(textRes)
    }

    override fun updateResetButtonVisibility(visible: Boolean) {
        button_reset.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun updateTimeText(text: String) {
        text_time.text = text
    }

}

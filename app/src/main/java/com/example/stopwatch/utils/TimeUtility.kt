package com.example.stopwatch.utils

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import java.util.*
import kotlin.concurrent.fixedRateTimer

fun setInterval(lifecycleOwner: LifecycleOwner, delegate: OnTimeUpdate, targetDelay: Long) {
    val timerId: String = UUID.randomUUID().toString()
    var uiHandler: Handler? = Handler(Looper.getMainLooper())
    var lastMillis = System.currentTimeMillis()

    var timer: Timer? = null
    timer = fixedRateTimer(timerId, period = targetDelay) {
        val realDelay = System.currentTimeMillis() - lastMillis
        lastMillis = System.currentTimeMillis()
        uiHandler?.post {
            if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
                delegate.onUpdate(realDelay, lastMillis)
            } else {
                // If no longer created, stop execution
                timer?.cancel()
                timer?.purge()
                timer = null
                uiHandler = null
            }
        }
    }
}
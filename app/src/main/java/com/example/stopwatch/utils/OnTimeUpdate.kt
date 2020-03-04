package com.example.stopwatch.utils

interface OnTimeUpdate {
    fun onUpdate(delay: Long, currentTimeMillis: Long)
}
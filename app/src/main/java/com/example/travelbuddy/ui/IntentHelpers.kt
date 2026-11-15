package com.example.travelbuddy.ui

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.travelbuddy.R

/**
 * Starts an implicit Intent. If no installed app can handle it
 * (for example no email app on an emulator), show a Toast instead of crashing.
 */
fun Context.startSafely(intent: Intent) {
    try {
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, R.string.error_no_app, Toast.LENGTH_SHORT).show()
    }
}

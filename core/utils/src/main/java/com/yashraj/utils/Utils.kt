package com.yashraj.utils

import android.text.format.DateFormat
import java.text.DecimalFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit

object Utils {
    fun formatDate(timestamp: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = timestamp * 1000L
        return DateFormat.format("dd-MM-yyyy", calendar).toString()
    }

    fun formatDurationMillis(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(hours)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(minutes) -
                TimeUnit.HOURS.toSeconds(hours)
        return if (hours > 0) {
            String.format("%02d:%02d:%02d", hours, minutes, seconds)
        } else {
            String.format("%02d:%02d", minutes, seconds)
        }
    }

    fun formatFileSize(size: Long): String {
        val b = size.toDouble()
        val k = size / 1024.0
        val m = size / 1024.0 / 1024.0
        val g = size / 1024.0 / 1024.0 / 1024.0
        val dec = DecimalFormat("0.00")
        val hrSize = if (g > 1) {
            dec.format(g).plus(" GB")
        } else if (m > 1) {
            dec.format(m).plus(" MB")
        } else if (k > 1) {
            dec.format(k).plus(" KB")
        } else {
            dec.format(b).plus(" Bytes")
        }
        return hrSize
    }
}
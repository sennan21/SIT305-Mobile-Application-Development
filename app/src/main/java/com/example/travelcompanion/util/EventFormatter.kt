package com.example.travelcompanion.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object EventFormatter {

    private val displayFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

    fun formatDateTime(timeMillis: Long): String {
        return displayFormat.format(Date(timeMillis))
    }
}

package com.example.travelcompanion.util

object EventValidator {

    fun validate(
        title: String,
        eventTimeMillis: Long?,
        isEditing: Boolean,
        nowMillis: Long = System.currentTimeMillis()
    ): String? {
        if (title.isBlank()) {
            return "Title is required"
        }

        if (eventTimeMillis == null) {
            return "Date and time are required"
        }

        // Existing events can still be edited even if their saved time is already in the past.
        if (!isEditing && eventTimeMillis < nowMillis) {
            return "Pick a future date and time"
        }

        return null
    }
}

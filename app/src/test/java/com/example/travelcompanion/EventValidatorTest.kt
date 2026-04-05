package com.example.travelcompanion

import com.example.travelcompanion.util.EventValidator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class EventValidatorTest {

    @Test
    fun blankTitle_returnsError() {
        val result = EventValidator.validate(
            title = "",
            eventTimeMillis = 2_000L,
            isEditing = false,
            nowMillis = 1_000L
        )

        assertEquals("Title is required", result)
    }

    @Test
    fun missingDate_returnsError() {
        val result = EventValidator.validate(
            title = "Flight",
            eventTimeMillis = null,
            isEditing = false,
            nowMillis = 1_000L
        )

        assertEquals("Date and time are required", result)
    }

    @Test
    fun pastDateForNewEvent_returnsError() {
        val result = EventValidator.validate(
            title = "Meeting",
            eventTimeMillis = 500L,
            isEditing = false,
            nowMillis = 1_000L
        )

        assertEquals("Pick a future date and time", result)
    }

    @Test
    fun validEdit_keepsPastDateAllowed() {
        val result = EventValidator.validate(
            title = "Old event",
            eventTimeMillis = 500L,
            isEditing = true,
            nowMillis = 1_000L
        )

        assertNull(result)
    }
}

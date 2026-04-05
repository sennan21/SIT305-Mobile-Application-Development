package com.example.travelcompanion.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val category: String,
    val location: String,
    val eventTimeMillis: Long,
    val createdAtMillis: Long = System.currentTimeMillis()
)

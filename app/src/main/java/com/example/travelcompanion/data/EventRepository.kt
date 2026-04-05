package com.example.travelcompanion.data

import kotlinx.coroutines.flow.Flow

class EventRepository(private val eventDao: EventDao) {

    fun getAllEvents(): Flow<List<EventEntity>> = eventDao.getAllEvents()

    suspend fun getEventById(id: Int): EventEntity? = eventDao.getEventById(id)

    suspend fun insert(event: EventEntity) = eventDao.insert(event)

    suspend fun update(event: EventEntity) = eventDao.update(event)

    suspend fun delete(event: EventEntity) = eventDao.delete(event)
}

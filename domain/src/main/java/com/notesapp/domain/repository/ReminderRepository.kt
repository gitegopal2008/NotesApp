package com.notesapp.domain.repository

import com.notesapp.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getUpcomingRemindersStream(): Flow<List<Reminder>>
    suspend fun getReminderById(id: Long): Reminder?
    suspend fun setReminder(reminder: Reminder): Long
    suspend fun cancelReminder(reminderId: Long)
    suspend fun completeReminder(reminderId: Long)
}

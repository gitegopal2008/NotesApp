package com.notesapp.data.repository

import com.notesapp.core.di.IoDispatcher
import com.notesapp.data.local.dao.ReminderDao
import com.notesapp.data.local.mapper.ReminderMapper.toDomain
import com.notesapp.data.local.mapper.ReminderMapper.toEntity
import com.notesapp.domain.model.Reminder
import com.notesapp.domain.repository.ReminderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ReminderRepository {

    override fun getUpcomingRemindersStream(): Flow<List<Reminder>> {
        return reminderDao.getUpcomingReminders(System.currentTimeMillis())
            .map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getReminderById(id: Long): Reminder? = withContext(ioDispatcher) {
        reminderDao.getRemindersForNote(id).first().firstOrNull()?.toDomain()
    }

    override suspend fun setReminder(reminder: Reminder): Long = withContext(ioDispatcher) {
        reminderDao.insertReminder(reminder.toEntity())
    }

    override suspend fun cancelReminder(reminderId: Long) = withContext(ioDispatcher) {
        reminderDao.deleteReminder(reminderId)
    }

    override suspend fun completeReminder(reminderId: Long) = withContext(ioDispatcher) {
        reminderDao.markCompleted(reminderId)
    }
}

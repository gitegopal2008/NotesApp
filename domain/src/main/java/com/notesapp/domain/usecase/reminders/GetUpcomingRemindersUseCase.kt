package com.notesapp.domain.usecase.reminders

import com.notesapp.domain.model.Reminder
import com.notesapp.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUpcomingRemindersUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    operator fun invoke(): Flow<List<Reminder>> {
        return repository.getUpcomingRemindersStream()
    }
}

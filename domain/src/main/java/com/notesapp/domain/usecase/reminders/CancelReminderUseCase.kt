package com.notesapp.domain.usecase.reminders

import com.notesapp.domain.repository.ReminderRepository
import javax.inject.Inject

class CancelReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: Long) {
        repository.cancelReminder(reminderId)
    }
}

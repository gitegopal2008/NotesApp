package com.notesapp.domain.usecase.reminders

import com.notesapp.domain.model.Reminder
import com.notesapp.domain.repository.ReminderRepository
import javax.inject.Inject

class SetReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(noteId: Long, time: Long, title: String): Result<Long> {
        val reminder = Reminder(
            noteId = noteId,
            title = title,
            time = time
        )
        return Result.success(repository.setReminder(reminder))
    }
}

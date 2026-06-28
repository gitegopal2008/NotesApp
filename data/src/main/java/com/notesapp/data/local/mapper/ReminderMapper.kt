package com.notesapp.data.local.mapper

import com.notesapp.data.local.entity.ReminderEntity
import com.notesapp.domain.model.Reminder

object ReminderMapper {

    fun ReminderEntity.toDomain(): Reminder = Reminder(
        id = id,
        noteId = noteId,
        title = title,
        time = time,
        isCompleted = isCompleted,
        repeatInterval = repeatInterval
    )

    fun Reminder.toEntity(): ReminderEntity = ReminderEntity(
        id = id,
        noteId = noteId,
        title = title,
        time = time,
        isCompleted = isCompleted,
        repeatInterval = repeatInterval
    )
}

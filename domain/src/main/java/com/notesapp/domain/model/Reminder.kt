package com.notesapp.domain.model

data class Reminder(
    val id: Long = 0,
    val noteId: Long,
    val title: String,
    val time: Long,
    val isCompleted: Boolean = false,
    val repeatInterval: String? = null // "NONE", "DAILY", "WEEKLY", "MONTHLY"
)

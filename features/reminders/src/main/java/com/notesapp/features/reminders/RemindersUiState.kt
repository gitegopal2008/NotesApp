package com.notesapp.features.reminders

import com.notesapp.domain.model.Reminder

data class RemindersUiState(
    val reminders: List<Reminder> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

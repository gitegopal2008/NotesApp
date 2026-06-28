package com.notesapp.features.notes

import com.notesapp.domain.model.Note

data class NoteDetailUiState(
    val note: Note? = null,
    val isNewNote: Boolean = false,
    val title: String = "",
    val content: String = "",
    val colorHex: Long = 0xFFFFFFFF,
    val folderId: Long? = null,
    val isDirty: Boolean = false,
    val isSaving: Boolean = false,
    val error: String? = null,
)

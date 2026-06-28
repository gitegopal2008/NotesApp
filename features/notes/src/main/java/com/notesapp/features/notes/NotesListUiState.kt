package com.notesapp.features.notes

import com.notesapp.domain.model.Note
import com.notesapp.domain.model.SortOrder

data class NotesListUiState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentFolderId: Long? = null,
    val currentFolderName: String? = null,
    val sortOrder: SortOrder = SortOrder.UpdatedAtDesc,
    val isGridView: Boolean = false,
    val searchQuery: String = "",
    val isSearchActive: Boolean = false,
)

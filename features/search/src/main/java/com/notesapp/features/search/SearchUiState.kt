package com.notesapp.features.search

import com.notesapp.domain.model.Note

data class SearchUiState(
    val query: String = "",
    val results: List<Note> = emptyList(),
    val isSearching: Boolean = false,
    val error: String? = null,
)

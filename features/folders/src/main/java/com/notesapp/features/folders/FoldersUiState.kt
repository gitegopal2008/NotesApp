package com.notesapp.features.folders

import com.notesapp.domain.model.Folder

data class FoldersUiState(
    val folders: List<Folder> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)

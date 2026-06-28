package com.notesapp.features.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.domain.model.Note
import com.notesapp.domain.usecase.notes.GetNoteByIdUseCase
import com.notesapp.domain.usecase.notes.SaveNoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteDetailUiState())
    val uiState: StateFlow<NoteDetailUiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NoteDetailUiState()
    )

    private var currentNoteId: Long = 0

    fun loadNote(noteId: Long) {
        currentNoteId = noteId
        if (noteId == -1L) {
            _uiState.update { it.copy(isNewNote = true) }
            return
        }
        viewModelScope.launch {
            getNoteByIdUseCase(noteId).collect { note ->
                if (note != null) {
                    _uiState.update {
                        it.copy(
                            note = note,
                            isNewNote = false,
                            title = note.title,
                            content = note.content,
                            colorHex = note.color,
                            folderId = note.folderId,
                        )
                    }
                }
            }
        }
    }

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title, isDirty = true) }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content, isDirty = true) }
    }

    fun updateColor(color: Long) {
        _uiState.update { it.copy(colorHex = color, isDirty = true) }
    }

    fun updateFolder(folderId: Long?) {
        _uiState.update { it.copy(folderId = folderId, isDirty = true) }
    }

    fun saveNote() {
        val state = _uiState.value
        if (state.isSaving) return
        if (state.title.isBlank() && state.content.isBlank()) return

        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            val note = Note(
                id = if (state.isNewNote) 0 else currentNoteId,
                title = state.title.trim(),
                content = state.content.trim(),
                color = state.colorHex,
                folderId = state.folderId,
            )
            val result = saveNoteUseCase(note)
            result.fold(
                onSuccess = {
                    _uiState.update { it.copy(isSaving = false, isDirty = false) }
                },
                onFailure = { e ->
                    _uiState.update {
                        it.copy(isSaving = false, error = e.message ?: "Failed to save note")
                    }
                }
            )
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

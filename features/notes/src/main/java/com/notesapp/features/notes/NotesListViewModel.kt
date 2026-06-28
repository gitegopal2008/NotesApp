package com.notesapp.features.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.domain.model.Note
import com.notesapp.domain.model.NoteFilter
import com.notesapp.domain.model.SortOrder
import com.notesapp.domain.usecase.folders.GetFoldersUseCase
import com.notesapp.domain.usecase.notes.DeleteNoteUseCase
import com.notesapp.domain.usecase.notes.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesListViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val getFoldersUseCase: GetFoldersUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(NotesListUiState())
    val uiState: StateFlow<NotesListUiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        NotesListUiState()
    )

    private val currentFilter = MutableStateFlow<NoteFilter>(NoteFilter.All)
    private val currentSort = MutableStateFlow(SortOrder.UPDATED_DESC)
    private val searchQuery = MutableStateFlow("")

    private var notesJob: Job? = null

    init {
        loadNotes()
        loadFolders()
    }

    fun loadNotes() {
        notesJob?.cancel()
        notesJob = viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            combine(
                currentFilter,
                currentSort,
                searchQuery
            ) { filter, sort, query ->
                val effectiveFilter = if (query.isNotBlank()) {
                    NoteFilter.Search(query)
                } else {
                    filter
                }
                Pair(effectiveFilter, sort)
            }.collect { (filter, sort) ->
                getNotesUseCase(filter, sort).collect { notes ->
                    _uiState.update {
                        it.copy(
                            notes = notes,
                            isLoading = false,
                            error = null
                        )
                    }
                }
            }
        }
    }

    fun loadNotesForFolder(folderId: Long, folderName: String?) {
        currentFilter.value = NoteFilter.ByFolder(folderId)
        _uiState.update { it.copy(currentFolderId = folderId, currentFolderName = folderName) }
        searchQuery.value = ""
    }

    fun loadAllNotes() {
        currentFilter.value = NoteFilter.All
        _uiState.update { it.copy(currentFolderId = null, currentFolderName = null) }
    }

    private fun loadFolders() {
        viewModelScope.launch {
            getFoldersUseCase().collect { folders ->
                val currentId = _uiState.value.currentFolderId
                if (currentId != null) {
                    val folder = folders.find { it.id == currentId }
                    _uiState.update { it.copy(currentFolderName = folder?.name) }
                }
            }
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deleteNoteUseCase(note)
        }
    }

    fun toggleView() {
        _uiState.update { it.copy(isGridView = !it.isGridView) }
    }

    fun changeSortOrder(sortOrder: SortOrder) {
        currentSort.value = sortOrder
        _uiState.update { it.copy(sortOrder = sortOrder) }
    }

    fun onSearchQueryChanged(query: String) {
        searchQuery.value = query
        _uiState.update { it.copy(searchQuery = query, isSearchActive = query.isNotBlank()) }
    }

    fun clearSearch() {
        searchQuery.value = ""
        _uiState.update { it.copy(searchQuery = "", isSearchActive = false) }
    }
}

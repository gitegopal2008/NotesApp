package com.notesapp.features.folders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.domain.usecase.folders.CreateFolderUseCase
import com.notesapp.domain.usecase.folders.DeleteFolderUseCase
import com.notesapp.domain.usecase.folders.GetFoldersUseCase
import com.notesapp.domain.usecase.folders.UpdateFolderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.notesapp.domain.model.Folder
import javax.inject.Inject

@HiltViewModel
class FoldersViewModel @Inject constructor(
    private val getFoldersUseCase: GetFoldersUseCase,
    private val createFolderUseCase: CreateFolderUseCase,
    private val deleteFolderUseCase: DeleteFolderUseCase,
    private val updateFolderUseCase: UpdateFolderUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(FoldersUiState())
    val uiState: StateFlow<FoldersUiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        FoldersUiState()
    )

    init {
        loadFolders()
    }

    fun loadFolders() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getFoldersUseCase().collect { folders ->
                _uiState.update {
                    it.copy(folders = folders, isLoading = false, error = null)
                }
            }
        }
    }

    fun createFolder(name: String, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            val result = createFolderUseCase(name)
            result.fold(
                onSuccess = { onResult(true) },
                onFailure = { e ->
                    _uiState.update { it.copy(error = e.message) }
                    onResult(false)
                }
            )
        }
    }

    fun deleteFolder(folder: Folder) {
        viewModelScope.launch {
            deleteFolderUseCase(folder)
        }
    }

    fun renameFolder(folder: Folder, newName: String) {
        viewModelScope.launch {
            val updated = folder.copy(name = newName)
            updateFolderUseCase(updated)
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}

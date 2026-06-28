package com.notesapp.features.backup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.domain.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BackupViewModel @Inject constructor(
    application: Application,
    private val backupManager: BackupManager,
    private val userPreferencesRepository: UserPreferencesRepository,
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(BackupUiState())
    val uiState: StateFlow<BackupUiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        BackupUiState()
    )

    init {
        loadBackupInfo()
    }

    private fun loadBackupInfo() {
        viewModelScope.launch {
            userPreferencesRepository.backupInfo.collect { info ->
                _uiState.update {
                    it.copy(
                        lastBackupTime = info.lastBackupTime,
                        backupCount = info.backupCount,
                        isAutoBackupEnabled = info.autoBackup
                    )
                }
            }
        }
    }

    fun createBackup() {
        viewModelScope.launch {
            _uiState.update { it.copy(isBackingUp = true, error = null, successMessage = null) }
            val result = backupManager.createBackup()
            result.fold(
                onSuccess = { fileName ->
                    _uiState.update {
                        it.copy(
                            isBackingUp = false,
                            successMessage = "Backup created: $fileName"
                        )
                    }
                },
                onFailure = { e ->
                    _uiState.update {
                        it.copy(isBackingUp = false, error = e.message ?: "Backup failed")
                    }
                }
            )
        }
    }

    fun restoreLatestBackup() {
        viewModelScope.launch {
            _uiState.update { it.copy(isRestoring = true, error = null, successMessage = null) }
            val files = backupManager.getBackupFiles()
            if (files.isEmpty()) {
                _uiState.update {
                    it.copy(isRestoring = false, error = "No backup files found")
                }
                return@launch
            }
            val result = backupManager.restoreBackup(files.first())
            result.fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(isRestoring = false, successMessage = "Restore completed")
                    }
                },
                onFailure = { e ->
                    _uiState.update {
                        it.copy(isRestoring = false, error = e.message ?: "Restore failed")
                    }
                }
            )
        }
    }

    fun clearMessages() {
        _uiState.update { it.copy(error = null, successMessage = null) }
    }
}

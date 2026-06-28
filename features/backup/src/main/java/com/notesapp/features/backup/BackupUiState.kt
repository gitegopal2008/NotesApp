package com.notesapp.features.backup

data class BackupUiState(
    val lastBackupTime: Long? = null,
    val backupCount: Int = 0,
    val isAutoBackupEnabled: Boolean = false,
    val isBackingUp: Boolean = false,
    val isRestoring: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null,
)

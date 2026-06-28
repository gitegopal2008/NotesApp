package com.notesapp.domain.model

data class BackupInfo(
    val lastBackupTime: Long? = null,
    val backupCount: Int = 0,
    val autoBackup: Boolean = false
)

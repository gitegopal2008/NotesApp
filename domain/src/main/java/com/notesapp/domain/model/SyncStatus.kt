package com.notesapp.domain.model

data class SyncStatus(
    val isSyncing: Boolean = false,
    val lastSyncAt: Long? = null,
    val lastError: String? = null
)

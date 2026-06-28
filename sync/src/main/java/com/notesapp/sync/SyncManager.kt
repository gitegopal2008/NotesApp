package com.notesapp.sync

import com.notesapp.domain.model.SyncStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SyncManager @Inject constructor() {

    private val _syncStatus = MutableStateFlow(SyncStatus())
    val syncStatus: StateFlow<SyncStatus> = _syncStatus.asStateFlow()

    suspend fun sync() {
        _syncStatus.value = _syncStatus.value.copy(isSyncing = true)
        try {
            // Future: cloud sync implementation
            _syncStatus.value = _syncStatus.value.copy(
                isSyncing = false,
                lastSyncAt = System.currentTimeMillis(),
                lastError = null
            )
        } catch (e: Exception) {
            _syncStatus.value = _syncStatus.value.copy(
                isSyncing = false,
                lastError = e.message
            )
        }
    }
}

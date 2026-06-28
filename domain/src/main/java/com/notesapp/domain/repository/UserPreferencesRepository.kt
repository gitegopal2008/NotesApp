package com.notesapp.domain.repository

import com.notesapp.domain.model.BackupInfo
import com.notesapp.domain.model.SortOrder
import com.notesapp.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {
    val themeMode: Flow<ThemeMode>
    val isDynamicColorEnabled: Flow<Boolean>
    val isBiometricEnabled: Flow<Boolean>
    val defaultSortOrder: Flow<SortOrder>
    val backupInfo: Flow<BackupInfo>

    suspend fun setThemeMode(mode: ThemeMode)
    suspend fun setDynamicColor(enabled: Boolean)
    suspend fun setBiometricEnabled(enabled: Boolean)
    suspend fun setDefaultSortOrder(order: SortOrder)
    suspend fun updateBackupInfo(info: BackupInfo)
}

package com.notesapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.notesapp.domain.model.BackupInfo
import com.notesapp.domain.model.SortOrder
import com.notesapp.domain.model.ThemeMode
import com.notesapp.domain.repository.UserPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "notes_app_prefs")

@Singleton
class UserPreferencesRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : UserPreferencesRepository {

    private object Keys {
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val DYNAMIC_COLOR = booleanPreferencesKey("dynamic_color")
        val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
        val DEFAULT_SORT = stringPreferencesKey("default_sort")
        val LAST_BACKUP_TIME = longPreferencesKey("last_backup_time")
        val BACKUP_COUNT = longPreferencesKey("backup_count")
        val AUTO_BACKUP = booleanPreferencesKey("auto_backup")
    }

    override val themeMode: Flow<ThemeMode> = context.dataStore.data.map { prefs ->
        when (prefs[Keys.THEME_MODE]) {
            "light" -> ThemeMode.LIGHT
            "dark" -> ThemeMode.DARK
            else -> ThemeMode.SYSTEM
        }
    }

    override val isDynamicColorEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.DYNAMIC_COLOR] ?: true
    }

    override val isBiometricEnabled: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[Keys.BIOMETRIC_ENABLED] ?: false
    }

    override val defaultSortOrder: Flow<SortOrder> = context.dataStore.data.map { prefs ->
        when (prefs[Keys.DEFAULT_SORT]) {
            "updated_asc" -> SortOrder.UPDATED_ASC
            "created_desc" -> SortOrder.CREATED_DESC
            "created_asc" -> SortOrder.CREATED_ASC
            "title_asc" -> SortOrder.TITLE_ASC
            "title_desc" -> SortOrder.TITLE_DESC
            else -> SortOrder.UPDATED_DESC
        }
    }

    override val backupInfo: Flow<BackupInfo> = context.dataStore.data.map { prefs ->
        BackupInfo(
            lastBackupTime = prefs[Keys.LAST_BACKUP_TIME],
            backupCount = prefs[Keys.BACKUP_COUNT]?.toInt() ?: 0,
            autoBackup = prefs[Keys.AUTO_BACKUP] ?: false
        )
    }

    override suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { prefs ->
            prefs[Keys.THEME_MODE] = when (mode) {
                ThemeMode.SYSTEM -> "system"
                ThemeMode.LIGHT -> "light"
                ThemeMode.DARK -> "dark"
            }
        }
    }

    override suspend fun setDynamicColor(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[Keys.DYNAMIC_COLOR] = enabled }
    }

    override suspend fun setBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { prefs -> prefs[Keys.BIOMETRIC_ENABLED] = enabled }
    }

    override suspend fun setDefaultSortOrder(order: SortOrder) {
        context.dataStore.edit { prefs ->
            prefs[Keys.DEFAULT_SORT] = when (order) {
                SortOrder.UPDATED_DESC -> "updated_desc"
                SortOrder.UPDATED_ASC -> "updated_asc"
                SortOrder.CREATED_DESC -> "created_desc"
                SortOrder.CREATED_ASC -> "created_asc"
                SortOrder.TITLE_ASC -> "title_asc"
                SortOrder.TITLE_DESC -> "title_desc"
            }
        }
    }

    override suspend fun updateBackupInfo(info: BackupInfo) {
        context.dataStore.edit { prefs ->
            info.lastBackupTime?.let { prefs[Keys.LAST_BACKUP_TIME] = it }
            prefs[Keys.BACKUP_COUNT] = info.backupCount.toLong()
            prefs[Keys.AUTO_BACKUP] = info.autoBackup
        }
    }
}

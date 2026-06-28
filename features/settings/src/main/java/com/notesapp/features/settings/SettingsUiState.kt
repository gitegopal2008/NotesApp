package com.notesapp.features.settings

import com.notesapp.domain.model.SortOrder
import com.notesapp.domain.model.ThemeMode

data class SettingsUiState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val isDynamicColorEnabled: Boolean = true,
    val isBiometricEnabled: Boolean = false,
    val defaultSortOrder: SortOrder = SortOrder.UPDATED_DESC,
)

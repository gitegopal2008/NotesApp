package com.notesapp.features.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.designsystem.component.NotesTopBar
import com.notesapp.domain.model.SortOrder
import com.notesapp.domain.model.ThemeMode

@Composable
fun SettingsScreen(
    onNavigateToBackup: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showThemeDropdown by remember { mutableStateOf(false) }
    var showSortDropdown by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            NotesTopBar(title = "Settings", onBackClick = onNavigateBack)
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            Text("Appearance", style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    // Theme
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { showThemeDropdown = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Theme", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                when (state.themeMode) {
                                    ThemeMode.SYSTEM -> "Follow system"
                                    ThemeMode.LIGHT -> "Light"
                                    ThemeMode.DARK -> "Dark"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = showThemeDropdown,
                        onDismissRequest = { showThemeDropdown = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Follow system") },
                            onClick = { viewModel.setThemeMode(ThemeMode.SYSTEM); showThemeDropdown = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Light") },
                            onClick = { viewModel.setThemeMode(ThemeMode.LIGHT); showThemeDropdown = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Dark") },
                            onClick = { viewModel.setThemeMode(ThemeMode.DARK); showThemeDropdown = false }
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                    // Dynamic color
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Dynamic color", style = MaterialTheme.typography.bodyLarge)
                            Text("Use Material You colors", style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = state.isDynamicColorEnabled,
                            onCheckedChange = viewModel::setDynamicColor
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Organization", style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { showSortDropdown = true },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Default sort order", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                when (state.defaultSortOrder) {
                                    SortOrder.UPDATED_DESC -> "Newest first"
                                    SortOrder.UPDATED_ASC -> "Oldest first"
                                    SortOrder.CREATED_DESC -> "Recently created"
                                    SortOrder.CREATED_ASC -> "Oldest created"
                                    SortOrder.TITLE_ASC -> "Title A-Z"
                                    SortOrder.TITLE_DESC -> "Title Z-A"
                                },
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                    }
                    DropdownMenu(
                        expanded = showSortDropdown,
                        onDismissRequest = { showSortDropdown = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Newest first") },
                            onClick = { viewModel.setDefaultSortOrder(SortOrder.UPDATED_DESC); showSortDropdown = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Oldest first") },
                            onClick = { viewModel.setDefaultSortOrder(SortOrder.UPDATED_ASC); showSortDropdown = false }
                        )
                        DropdownMenuItem(
                            text = { Text("Title A-Z") },
                            onClick = { viewModel.setDefaultSortOrder(SortOrder.TITLE_ASC); showSortDropdown = false }
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Security", style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Biometric lock", style = MaterialTheme.typography.bodyLarge)
                            Text("Require biometric to open app",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        Switch(
                            checked = state.isBiometricEnabled,
                            onCheckedChange = viewModel::setBiometricEnabled
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Data", style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth().clickable(onClick = onNavigateToBackup),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Backup & Restore", style = MaterialTheme.typography.bodyLarge)
                        Text("Export and import your notes",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                }
            }

            Spacer(Modifier.height(32.dp))
            Text("NotesApp v1.0.0", style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

package com.notesapp.features.folders

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.designsystem.component.EmptyState
import com.notesapp.designsystem.component.ErrorState
import com.notesapp.designsystem.component.FolderCard
import com.notesapp.designsystem.component.FolderDisplayModel
import com.notesapp.designsystem.component.LoadingIndicator
import com.notesapp.designsystem.component.NotesTopBar

@Composable
fun FoldersScreen(
    onFolderClick: (Long, String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: FoldersViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showCreateDialog by remember { mutableStateOf(false) }
    var newFolderName by remember { mutableStateOf("") }
    var renameFolderId by remember { mutableStateOf<Long?>(null) }
    var renameText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            NotesTopBar(
                title = "Folders",
                onBackClick = onNavigateBack
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showCreateDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "New folder")
            }
        }
    ) { padding ->
        when {
            state.isLoading -> LoadingIndicator()
            state.error != null -> ErrorState(
                message = state.error!!,
                onRetry = { viewModel.loadFolders() }
            )
            state.folders.isEmpty() -> EmptyState(
                icon = Icons.Filled.CreateNewFolder,
                title = "No folders yet",
                description = "Create folders to organize your notes"
            )
            else -> LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(padding)
            ) {
                items(state.folders, key = { it.id }) { folder ->
                    FolderCard(
                        folder = FolderDisplayModel(
                            id = folder.id,
                            name = folder.name,
                            noteCount = 0,
                            color = folder.color
                        ),
                        onClick = { onFolderClick(folder.id, folder.name) }
                    )
                }
            }
        }
    }

    if (showCreateDialog) {
        AlertDialog(
            onDismissRequest = { showCreateDialog = false; newFolderName = "" },
            title = { Text("New Folder") },
            text = {
                OutlinedTextField(
                    value = newFolderName,
                    onValueChange = { newFolderName = it },
                    label = { Text("Folder name") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (newFolderName.isNotBlank()) {
                        viewModel.createFolder(newFolderName.trim()) { success ->
                            if (success) {
                                showCreateDialog = false
                                newFolderName = ""
                            }
                        }
                    }
                }) { Text("Create") }
            },
            dismissButton = {
                TextButton(onClick = { showCreateDialog = false; newFolderName = "" }) {
                    Text("Cancel")
                }
            }
        )
    }
}

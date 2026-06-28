package com.notesapp.features.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CreateNewFolder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ViewList
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.designsystem.component.EmptyState
import com.notesapp.designsystem.component.ErrorState
import com.notesapp.designsystem.component.LoadingIndicator
import com.notesapp.designsystem.component.NoteCard
import com.notesapp.designsystem.component.NoteCardGrid
import com.notesapp.designsystem.component.NoteDisplayModel
import com.notesapp.designsystem.component.NotesSearchBar
import com.notesapp.designsystem.component.NotesTopBar
import com.notesapp.designsystem.component.SortDropdown
import com.notesapp.designsystem.component.SortOption
import com.notesapp.domain.model.SortOrder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun NotesScreen(
    onNoteClick: (Long) -> Unit,
    onNewNote: () -> Unit,
    onNavigateToFolders: () -> Unit,
    onNavigateToSearch: () -> Unit,
    viewModel: NotesListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (state.isSearchActive) {
                NotesSearchBar(
                    query = state.searchQuery,
                    onQueryChange = viewModel::onSearchQueryChanged,
                    onClear = { viewModel.clearSearch() }
                )
            } else {
                NotesTopBar(
                    title = state.currentFolderName ?: "Notes",
                    actions = {
                        IconButton(onClick = { viewModel.toggleView() }) {
                            Icon(
                                if (state.isGridView) Icons.Filled.ViewList else Icons.Filled.ViewModule,
                                contentDescription = "Toggle view"
                            )
                        }
                        IconButton(onClick = onNavigateToSearch) {
                            Icon(Icons.Filled.Search, contentDescription = "Search")
                        }
                        IconButton(onClick = onNavigateToFolders) {
                            Icon(Icons.Filled.CreateNewFolder, contentDescription = "Folders")
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNewNote) {
                Icon(Icons.Filled.Add, contentDescription = "New note")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (!state.isSearchActive) {
                val sortOptions = listOf(
                    SortOption("Updated", "UPDATED_DESC"),
                    SortOption("Created", "CREATED_DESC"),
                    SortOption("Title A-Z", "TITLE_ASC"),
                )
                val currentSortOption = when (state.sortOrder) {
                    SortOrder.UPDATED_DESC -> sortOptions[0]
                    SortOrder.CREATED_DESC -> sortOptions[1]
                    SortOrder.TITLE_ASC -> sortOptions[2]
                    else -> sortOptions[0]
                }
                SortDropdown(
                    selected = currentSortOption,
                    options = sortOptions,
                    onSelected = { option ->
                        val order = when (option.value) {
                            "UPDATED_DESC" -> SortOrder.UPDATED_DESC
                            "CREATED_DESC" -> SortOrder.CREATED_DESC
                            "TITLE_ASC" -> SortOrder.TITLE_ASC
                            else -> SortOrder.UPDATED_DESC
                        }
                        viewModel.changeSortOrder(order)
                    }
                )
            }

            when {
                state.isLoading -> LoadingIndicator()
                state.error != null -> ErrorState(
                    message = state.error!!,
                    onRetry = { viewModel.loadNotes() }
                )
                state.notes.isEmpty() -> EmptyState(
                    icon = if (state.isSearchActive) Icons.Filled.Search else Icons.Filled.Add,
                    title = if (state.isSearchActive) "No results" else "No notes yet",
                    description = if (state.isSearchActive) "Try a different search" else "Tap + to create your first note"
                )
                else -> {
                    if (state.isGridView) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            contentPadding = PaddingValues(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.notes, key = { it.id }) { note ->
                                NoteCardGrid(
                                    note = note.toDisplayModel(dateFormat),
                                    onClick = { onNoteClick(note.id) }
                                )
                            }
                        }
                    } else {
                        LazyColumn(
                            contentPadding = PaddingValues(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(state.notes, key = { it.id }) { note ->
                                NoteCard(
                                    note = note.toDisplayModel(dateFormat),
                                    onClick = { onNoteClick(note.id) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun com.notesapp.domain.model.Note.toDisplayModel(dateFormat: SimpleDateFormat): NoteDisplayModel {
    return NoteDisplayModel(
        id = id,
        title = title,
        preview = content,
        isPinned = isFavorite,
        color = color,
        updatedAt = dateFormat.format(Date(updatedAt)),
        folderName = null
    )
}

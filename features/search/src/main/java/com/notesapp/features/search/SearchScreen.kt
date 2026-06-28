package com.notesapp.features.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.notesapp.designsystem.component.EmptyState
import com.notesapp.designsystem.component.LoadingIndicator
import com.notesapp.designsystem.component.NoteCard
import com.notesapp.designsystem.component.NoteDisplayModel
import com.notesapp.designsystem.component.NotesSearchBar
import com.notesapp.designsystem.component.NotesTopBar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun SearchScreen(
    onNoteClick: (Long) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    Scaffold(
        topBar = {
            NotesTopBar(title = "Search", onBackClick = onNavigateBack)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                NotesSearchBar(
                    query = state.query,
                    onQueryChange = viewModel::onQueryChanged,
                    onClear = { viewModel.clearSearch() },
                    placeholder = "Search notes..."
                )
            }

            when {
                state.isSearching -> item { LoadingIndicator() }
                state.query.isBlank() -> item {
                    EmptyState(
                        icon = Icons.Filled.Search,
                        title = "Search your notes",
                        description = "Type above to search through all your notes"
                    )
                }
                state.results.isEmpty() -> item {
                    EmptyState(
                        icon = Icons.Filled.Search,
                        title = "No results",
                        description = "Try a different search term"
                    )
                }
                else -> items(state.results, key = { it.id }) { note ->
                    NoteCard(
                        note = NoteDisplayModel(
                            id = note.id,
                            title = note.title,
                            preview = note.content,
                            isPinned = note.isFavorite,
                            color = note.color,
                            updatedAt = dateFormat.format(Date(note.updatedAt))
                        ),
                        onClick = { onNoteClick(note.id) }
                    )
                }
            }
        }
    }
}

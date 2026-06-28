package com.notesapp.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.notesapp.features.backup.BackupScreen
import com.notesapp.features.folders.FoldersScreen
import com.notesapp.features.notes.NoteDetailScreen
import com.notesapp.features.notes.NotesScreen
import com.notesapp.features.search.SearchScreen
import com.notesapp.features.settings.SettingsScreen

@Composable
fun NotesNavGraph(navController: NavHostController) {
    val actions = remember(navController) { NavigationActions(navController) }

    NavHost(
        navController = navController,
        startDestination = Screen.Notes.route
    ) {
        composable(Screen.Notes.route) {
            NotesScreen(
                onNoteClick = { noteId -> actions.navigateToNoteDetail(noteId) },
                onNewNote = { actions.navigateToNewNote() },
                onNavigateToFolders = { navController.navigate(Screen.Folders.route) },
                onNavigateToSearch = { navController.navigate(Screen.Search.route) }
            )
        }

        composable(
            route = Screen.NoteDetail.route,
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: -1L
            NoteDetailScreen(
                noteId = noteId,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Folders.route) {
            FoldersScreen(
                onFolderClick = { folderId, folderName ->
                    navController.previousBackStackEntry?.savedStateHandle?.apply {
                        set("folderId", folderId)
                        set("folderName", folderName)
                    }
                    navController.popBackStack()
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Search.route) {
            SearchScreen(
                onNoteClick = { noteId -> actions.navigateToNoteDetail(noteId) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onNavigateToBackup = { navController.navigate(Screen.Backup.route) },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Backup.route) {
            BackupScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}

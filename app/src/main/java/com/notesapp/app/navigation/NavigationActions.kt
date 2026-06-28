package com.notesapp.app.navigation

import androidx.navigation.NavHostController

class NavigationActions(private val navController: NavHostController) {

    fun navigateToNoteDetail(noteId: Long) {
        navController.navigate(Screen.NoteDetail.createRoute(noteId))
    }

    fun navigateToNewNote() {
        navController.navigate(Screen.NoteDetail.createRoute(-1L))
    }

    fun navigateToFolders() {
        navController.navigate(Screen.Folders.route) {
            popUpTo(Screen.Notes.route) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToSearch() {
        navController.navigate(Screen.Search.route) {
            popUpTo(Screen.Notes.route) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToSettings() {
        navController.navigate(Screen.Settings.route) {
            popUpTo(Screen.Notes.route) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToBackup() {
        navController.navigate(Screen.Backup.route)
    }

    fun goBack() {
        navController.popBackStack()
    }

    fun popUpToTop() {
        navController.popBackStack(Screen.Notes.route, inclusive = false)
    }
}

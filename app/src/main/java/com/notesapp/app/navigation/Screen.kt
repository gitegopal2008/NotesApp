package com.notesapp.app.navigation

sealed class Screen(val route: String, val title: String) {
    data object Notes : Screen("notes", "Notes")
    data object NoteDetail : Screen("note/{noteId}", "Note") {
        fun createRoute(noteId: Long) = "note/$noteId"
    }
    data object Folders : Screen("folders", "Folders")
    data object Search : Screen("search", "Search")
    data object Settings : Screen("settings", "Settings")
    data object Backup : Screen("backup", "Backup")
}

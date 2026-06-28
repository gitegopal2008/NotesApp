package com.notesapp.analytics

sealed class AnalyticsScreen(val name: String) {
    data object NoteList : AnalyticsScreen("note_list")
    data class NoteDetail(val noteId: Long) : AnalyticsScreen("note_detail")
    data object FoldersList : AnalyticsScreen("folders_list")
    data object Search : AnalyticsScreen("search")
    data object Settings : AnalyticsScreen("settings")
    data object Backup : AnalyticsScreen("backup")
}

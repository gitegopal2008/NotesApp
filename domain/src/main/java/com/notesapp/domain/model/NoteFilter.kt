package com.notesapp.domain.model

sealed class NoteFilter {
    data object All : NoteFilter()
    data class ByFolder(val folderId: Long) : NoteFilter()
    data class ByTag(val tag: String) : NoteFilter()
    data object Favorites : NoteFilter()
    data object Archived : NoteFilter()
    data object Trash : NoteFilter()
    data class Search(val query: String) : NoteFilter()
}

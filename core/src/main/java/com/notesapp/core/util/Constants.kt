package com.notesapp.core.util

object Constants {
    const val DATABASE_NAME = "notes_app_db"
    const val BACKUP_DIR = "NotesAppBackup"
    const val PREFS_NAME = "notes_app_prefs"
    const val NOTE_CONTENT_FTS_TABLE = "notes_fts"
    const val DEFAULT_PAGE_SIZE = 50
    const val MAX_NOTE_TITLE_LENGTH = 200
    const val MAX_FOLDER_NAME_LENGTH = 50
    const val SEARCH_DEBOUNCE_MS = 300L
    const val AUTO_SAVE_DEBOUNCE_MS = 2000L
}

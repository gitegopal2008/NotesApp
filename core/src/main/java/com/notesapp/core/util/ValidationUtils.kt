package com.notesapp.core.util

object ValidationUtils {

    fun isValidNoteTitle(title: String): Boolean {
        return title.isNotBlank() && title.length <= Constants.MAX_NOTE_TITLE_LENGTH
    }

    fun isValidFolderName(name: String): Boolean {
        return name.isNotBlank() && name.length <= Constants.MAX_FOLDER_NAME_LENGTH
    }
}

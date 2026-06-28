package com.notesapp.domain.model

data class Note(
    val id: Long = 0,
    val title: String = "",
    val content: String = "",
    val folderId: Long? = null,
    val isFavorite: Boolean = false,
    val isArchived: Boolean = false,
    val color: Long = 0xFFFFFFFF,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val reminderTime: Long? = null,
    val tags: List<String> = emptyList(),
    val isEncrypted: Boolean = false,
    val isDeleted: Boolean = false,
    val deletedAt: Long? = null
) {
    companion object {
        val EMPTY = Note()
    }
}

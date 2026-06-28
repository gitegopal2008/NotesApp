package com.notesapp.data.local.mapper

import com.notesapp.data.local.entity.NoteEntity
import com.notesapp.domain.model.Note
import org.json.JSONArray
import org.json.JSONObject

object NoteMapper {

    fun NoteEntity.toDomain(): Note = Note(
        id = id,
        title = title,
        content = content,
        folderId = folderId,
        isFavorite = isFavorite,
        isArchived = isArchived,
        color = color,
        createdAt = createdAt,
        updatedAt = updatedAt,
        reminderTime = reminderTime,
        tags = parseTags(tagsJson),
        isEncrypted = isEncrypted,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

    fun Note.toEntity(): NoteEntity = NoteEntity(
        id = id,
        title = title,
        content = content,
        folderId = folderId,
        isFavorite = isFavorite,
        isArchived = isArchived,
        color = color,
        createdAt = createdAt,
        updatedAt = updatedAt,
        reminderTime = reminderTime,
        tagsJson = encodeTags(tags),
        isEncrypted = isEncrypted,
        isDeleted = isDeleted,
        deletedAt = deletedAt
    )

    private fun parseTags(json: String): List<String> {
        if (json.isBlank()) return emptyList()
        val arr = JSONArray(json)
        return (0 until arr.length()).map { arr.getString(it) }
    }

    private fun encodeTags(tags: List<String>): String {
        return JSONArray(tags).toString()
    }
}

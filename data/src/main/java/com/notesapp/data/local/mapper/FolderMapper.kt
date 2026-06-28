package com.notesapp.data.local.mapper

import com.notesapp.data.local.entity.FolderEntity
import com.notesapp.domain.model.Folder

object FolderMapper {

    fun FolderEntity.toDomain(noteCount: Int = 0): Folder = Folder(
        id = id,
        name = name,
        parentId = parentId,
        color = color,
        icon = icon,
        createdAt = createdAt,
        updatedAt = updatedAt
    )

    fun Folder.toEntity(): FolderEntity = FolderEntity(
        id = id,
        name = name,
        parentId = parentId,
        color = color,
        icon = icon,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

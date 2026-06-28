package com.notesapp.domain.repository

import com.notesapp.domain.model.Folder
import kotlinx.coroutines.flow.Flow

interface FolderRepository {
    fun getFoldersStream(): Flow<List<Folder>>
    suspend fun getFolderById(id: Long): Folder?
    suspend fun createFolder(name: String, parentId: Long? = null): Long
    suspend fun updateFolder(folder: Folder)
    suspend fun deleteFolder(folder: Folder)
    fun getNoteCountForFolder(folderId: Long): Flow<Int>
}

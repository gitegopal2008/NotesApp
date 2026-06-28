package com.notesapp.data.repository

import com.notesapp.core.di.IoDispatcher
import com.notesapp.data.local.dao.FolderDao
import com.notesapp.data.local.mapper.FolderMapper.toDomain
import com.notesapp.data.local.mapper.FolderMapper.toEntity
import com.notesapp.domain.model.Folder
import com.notesapp.domain.repository.FolderRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FolderRepository {

    override fun getFoldersStream(): Flow<List<Folder>> {
        return folderDao.getAllFolders().map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getFolderById(id: Long): Folder? = withContext(ioDispatcher) {
        folderDao.getFolderById(id).first()?.toDomain()
    }

    override suspend fun createFolder(name: String, parentId: Long?): Long = withContext(ioDispatcher) {
        val now = System.currentTimeMillis()
        val entity = com.notesapp.data.local.entity.FolderEntity(
            name = name,
            parentId = parentId,
            createdAt = now,
            updatedAt = now
        )
        folderDao.insertFolder(entity)
    }

    override suspend fun updateFolder(folder: Folder) = withContext(ioDispatcher) {
        folderDao.updateFolder(folder.toEntity())
    }

    override suspend fun deleteFolder(folder: Folder) = withContext(ioDispatcher) {
        folderDao.deleteFolderById(folder.id)
    }

    override fun getNoteCountForFolder(folderId: Long): Flow<Int> {
        return folderDao.getNoteCountForFolder(folderId)
    }
}

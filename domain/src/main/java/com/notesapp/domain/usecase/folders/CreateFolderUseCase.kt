package com.notesapp.domain.usecase.folders

import com.notesapp.domain.repository.FolderRepository
import javax.inject.Inject

class CreateFolderUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(name: String, parentId: Long? = null): Result<Long> {
        if (name.isBlank()) {
            return Result.failure(IllegalArgumentException("Folder name cannot be empty"))
        }
        return Result.success(repository.createFolder(name.trim(), parentId))
    }
}

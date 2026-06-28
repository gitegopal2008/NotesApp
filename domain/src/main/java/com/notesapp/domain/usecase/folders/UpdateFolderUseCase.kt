package com.notesapp.domain.usecase.folders

import com.notesapp.domain.model.Folder
import com.notesapp.domain.repository.FolderRepository
import javax.inject.Inject

class UpdateFolderUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(folder: Folder): Result<Unit> {
        return if (folder.name.isBlank()) {
            Result.failure(IllegalArgumentException("Folder name cannot be empty"))
        } else {
            repository.updateFolder(folder)
            Result.success(Unit)
        }
    }
}

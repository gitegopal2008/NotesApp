package com.notesapp.domain.usecase.folders

import com.notesapp.domain.model.Folder
import com.notesapp.domain.repository.FolderRepository
import javax.inject.Inject

class DeleteFolderUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    suspend operator fun invoke(folder: Folder) {
        repository.deleteFolder(folder)
    }
}

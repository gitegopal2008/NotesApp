package com.notesapp.domain.usecase.folders

import com.notesapp.domain.model.Folder
import com.notesapp.domain.repository.FolderRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFoldersUseCase @Inject constructor(
    private val repository: FolderRepository
) {
    operator fun invoke(): Flow<List<Folder>> = repository.getFoldersStream()
}

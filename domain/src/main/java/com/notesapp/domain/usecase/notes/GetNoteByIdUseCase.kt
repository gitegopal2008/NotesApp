package com.notesapp.domain.usecase.notes

import com.notesapp.domain.model.Note
import com.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoteByIdUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(id: Long): Flow<Note?> {
        return repository.getNoteByIdFlow(id)
    }
}

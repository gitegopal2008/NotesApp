package com.notesapp.domain.usecase.notes

import com.notesapp.domain.model.Note
import com.notesapp.domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note) {
        repository.softDeleteNote(note)
    }
}

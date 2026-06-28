package com.notesapp.domain.usecase.notes

import com.notesapp.domain.model.Note
import com.notesapp.domain.repository.NoteRepository
import javax.inject.Inject

class SaveNoteUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note): Result<Long> {
        if (note.title.isBlank() && note.content.isBlank()) {
            return Result.failure(IllegalArgumentException("Note must have a title or content"))
        }
        return if (note.id == 0L) {
            Result.success(repository.insertNote(note))
        } else {
            repository.updateNote(note)
            Result.success(note.id)
        }
    }
}

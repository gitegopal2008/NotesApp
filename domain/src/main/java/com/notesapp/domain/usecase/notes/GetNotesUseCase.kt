package com.notesapp.domain.usecase.notes

import com.notesapp.domain.model.Note
import com.notesapp.domain.model.NoteFilter
import com.notesapp.domain.model.SortOrder
import com.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val repository: NoteRepository
) {
    operator fun invoke(filter: NoteFilter = NoteFilter.All, sort: SortOrder = SortOrder.UPDATED_DESC): Flow<List<Note>> {
        return repository.getNotesStream(filter, sort)
    }
}

package com.notesapp.domain.repository

import com.notesapp.domain.model.Note
import com.notesapp.domain.model.NoteFilter
import com.notesapp.domain.model.SortOrder
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotesStream(filter: NoteFilter, sort: SortOrder): Flow<List<Note>>
    suspend fun getNoteById(id: Long): Note?
    fun getNoteByIdFlow(id: Long): Flow<Note?>
    suspend fun insertNote(note: Note): Long
    suspend fun updateNote(note: Note)
    suspend fun deleteNote(note: Note)
    suspend fun softDeleteNote(note: Note)
    suspend fun permanentlyDeleteNote(note: Note)
    suspend fun searchNotes(query: String): Flow<List<Note>>
    suspend fun getNoteCount(): Int
}

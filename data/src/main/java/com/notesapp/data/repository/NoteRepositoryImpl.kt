package com.notesapp.data.repository

import com.notesapp.core.di.IoDispatcher
import com.notesapp.data.local.dao.NoteDao
import com.notesapp.data.local.mapper.NoteMapper.toDomain
import com.notesapp.data.local.mapper.NoteMapper.toEntity
import com.notesapp.domain.model.Note
import com.notesapp.domain.model.NoteFilter
import com.notesapp.domain.model.SortOrder
import com.notesapp.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : NoteRepository {

    override fun getNotesStream(filter: NoteFilter, sort: SortOrder): Flow<List<Note>> {
        val query = when (filter) {
            is NoteFilter.All -> noteDao.getAllNotes()
            is NoteFilter.ByFolder -> noteDao.getNotesByFolder(filter.folderId)
            is NoteFilter.ByTag -> noteDao.searchNotesFts(filter.tag)
            is NoteFilter.Favorites -> noteDao.getFavoriteNotes()
            is NoteFilter.Archived -> noteDao.getArchivedNotes()
            is NoteFilter.Trash -> noteDao.getDeletedNotes()
            is NoteFilter.Search -> noteDao.searchNotesFts(filter.query)
        }
        return query.map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getNoteById(id: Long): Note? = withContext(ioDispatcher) {
        noteDao.getNoteByIdOnce(id)?.toDomain()
    }

    override fun getNoteByIdFlow(id: Long): Flow<Note?> {
        return noteDao.getNoteById(id).map { it?.toDomain() }
    }

    override suspend fun insertNote(note: Note): Long = withContext(ioDispatcher) {
        noteDao.insertNote(note.toEntity())
    }

    override suspend fun updateNote(note: Note) = withContext(ioDispatcher) {
        noteDao.updateNote(note.toEntity())
    }

    override suspend fun deleteNote(note: Note) = withContext(ioDispatcher) {
        noteDao.softDeleteNote(note.id)
    }

    override suspend fun softDeleteNote(note: Note) = withContext(ioDispatcher) {
        noteDao.softDeleteNote(note.id, System.currentTimeMillis())
    }

    override suspend fun searchNotes(query: String): Flow<List<Note>> {
        return noteDao.searchNotesFts(query).map { entities -> entities.map { it.toDomain() } }
    }

    override suspend fun getNoteCount(): Int = withContext(ioDispatcher) {
        noteDao.getNoteCount()
    }
}

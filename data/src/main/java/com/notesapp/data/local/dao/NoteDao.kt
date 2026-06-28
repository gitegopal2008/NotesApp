package com.notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.notesapp.data.local.entity.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes WHERE is_deleted = 0 AND is_archived = 0 ORDER BY updated_at DESC")
    fun getAllNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_deleted = 0 AND is_archived = 0 AND folder_id = :folderId ORDER BY updated_at DESC")
    fun getNotesByFolder(folderId: Long): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_deleted = 0 AND is_favorite = 1 ORDER BY updated_at DESC")
    fun getFavoriteNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_deleted = 0 AND is_archived = 1 ORDER BY updated_at DESC")
    fun getArchivedNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE is_deleted = 1 ORDER BY deleted_at DESC")
    fun getDeletedNotes(): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getNoteById(id: Long): Flow<NoteEntity?>

    @Query("SELECT * FROM notes WHERE id = :id")
    suspend fun getNoteByIdOnce(id: Long): NoteEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: NoteEntity): Long

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Query("UPDATE notes SET is_deleted = 1, deleted_at = :deletedAt WHERE id = :id")
    suspend fun softDeleteNote(id: Long, deletedAt: Long = System.currentTimeMillis())

    @Query("DELETE FROM notes WHERE id = :id")
    suspend fun hardDeleteNote(id: Long)

    @Query("SELECT notes.* FROM notes JOIN notes_fts ON notes.rowid = notes_fts.rowid WHERE notes_fts MATCH :query")
    fun searchNotesFts(query: String): Flow<List<NoteEntity>>

    @Query("SELECT COUNT(*) FROM notes WHERE is_deleted = 0")
    suspend fun getNoteCount(): Int

    @Query("DELETE FROM notes WHERE is_deleted = 1")
    suspend fun deleteAllTrash()

    @Query("SELECT * FROM notes")
    suspend fun getAllNotesOnce(): List<NoteEntity>

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}

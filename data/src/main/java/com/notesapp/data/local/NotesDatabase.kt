package com.notesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.notesapp.data.local.dao.FolderDao
import com.notesapp.data.local.dao.NoteDao
import com.notesapp.data.local.dao.ReminderDao
import com.notesapp.data.local.entity.FolderEntity
import com.notesapp.data.local.entity.NoteEntity
import com.notesapp.data.local.entity.NoteFtsEntity
import com.notesapp.data.local.entity.ReminderEntity

@Database(
    entities = [NoteEntity::class, NoteFtsEntity::class, FolderEntity::class, ReminderEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
    abstract fun folderDao(): FolderDao
    abstract fun reminderDao(): ReminderDao

    companion object {
        const val DATABASE_NAME = "notes_app_db"
    }
}

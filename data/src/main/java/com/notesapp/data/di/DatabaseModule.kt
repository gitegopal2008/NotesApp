package com.notesapp.data.di

import android.content.Context
import androidx.room.Room
import com.notesapp.data.local.NotesDatabase
import com.notesapp.data.local.dao.FolderDao
import com.notesapp.data.local.dao.NoteDao
import com.notesapp.data.local.dao.ReminderDao
import com.notesapp.data.repository.FolderRepositoryImpl
import com.notesapp.data.repository.NoteRepositoryImpl
import com.notesapp.data.repository.ReminderRepositoryImpl
import com.notesapp.data.repository.UserPreferencesRepositoryImpl
import com.notesapp.domain.repository.FolderRepository
import com.notesapp.domain.repository.NoteRepository
import com.notesapp.domain.repository.ReminderRepository
import com.notesapp.domain.repository.UserPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NotesDatabase {
        return Room.databaseBuilder(
            context,
            NotesDatabase::class.java,
            NotesDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideNoteDao(db: NotesDatabase): NoteDao = db.noteDao()

    @Provides
    fun provideFolderDao(db: NotesDatabase): FolderDao = db.folderDao()

    @Provides
    fun provideReminderDao(db: NotesDatabase): ReminderDao = db.reminderDao()

    @Provides
    @Singleton
    fun provideNoteRepository(impl: NoteRepositoryImpl): NoteRepository = impl

    @Provides
    @Singleton
    fun provideFolderRepository(impl: FolderRepositoryImpl): FolderRepository = impl

    @Provides
    @Singleton
    fun provideReminderRepository(impl: ReminderRepositoryImpl): ReminderRepository = impl

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(impl: UserPreferencesRepositoryImpl): UserPreferencesRepository = impl
}

package com.notesapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.notesapp.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminders WHERE is_completed = 0 AND time <= :currentTime ORDER BY time ASC")
    fun getUpcomingReminders(currentTime: Long): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM reminders WHERE note_id = :noteId ORDER BY time ASC")
    fun getRemindersForNote(noteId: Long): Flow<List<ReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: ReminderEntity): Long

    @Update
    suspend fun updateReminder(reminder: ReminderEntity)

    @Query("DELETE FROM reminders WHERE id = :id")
    suspend fun deleteReminder(id: Long)

    @Query("UPDATE reminders SET is_completed = 1 WHERE id = :id")
    suspend fun markCompleted(id: Long)
}

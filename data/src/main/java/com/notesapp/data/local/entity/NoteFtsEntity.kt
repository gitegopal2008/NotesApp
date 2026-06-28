package com.notesapp.data.local.entity

import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.ColumnInfo

@Fts4(contentEntity = NoteEntity::class)
@Entity(tableName = "notes_fts")
data class NoteFtsEntity(
    @ColumnInfo(name = "title") val title: String = "",
    @ColumnInfo(name = "content") val content: String = ""
)

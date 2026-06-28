package com.notesapp.features.backup

import android.content.Context
import com.notesapp.data.local.NotesDatabase
import com.notesapp.data.local.entity.FolderEntity
import com.notesapp.data.local.entity.NoteEntity
import com.notesapp.domain.model.BackupInfo
import com.notesapp.domain.repository.UserPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: NotesDatabase,
    private val userPreferencesRepository: UserPreferencesRepository,
) {

    private val backupDir: File
        get() = File(context.filesDir, "backups").also { it.mkdirs() }

    suspend fun createBackup(): Result<String> {
        return try {
            val backup = buildBackupJson()
            val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US)
                .format(Date())
            val fileName = "notes_backup_$timestamp.json"
            val file = File(backupDir, fileName)
            file.writeText(backup.toString(2))

            val info = BackupInfo(
                lastBackupTime = System.currentTimeMillis(),
                backupCount = backupDir.listFiles()?.size ?: 1,
                autoBackup = true
            )
            userPreferencesRepository.updateBackupInfo(info)
            Result.success(fileName)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun restoreBackup(fileName: String): Result<Unit> {
        return try {
            val file = File(backupDir, fileName)
            if (!file.exists()) {
                return Result.failure(IllegalArgumentException("Backup file not found"))
            }
            val json = JSONObject(file.readText())
            val dao = database.noteDao()
            val folderDao = database.folderDao()

            // Clear existing data
            dao.deleteAllNotes()
            folderDao.deleteAllFolders()

            // Restore folders
            val folders = json.getJSONArray("folders")
            for (i in 0 until folders.length()) {
                val f = folders.getJSONObject(i)
                folderDao.insertFolder(
                    FolderEntity(
                        name = f.getString("name"),
                        color = f.optLong("color", 0xFF8B5CF6),
                        parentId = if (f.has("parentId") && !f.isNull("parentId")) f.optLong("parentId") else null,
                    )
                )
            }

            // Restore notes
            val notes = json.getJSONArray("notes")
            for (i in 0 until notes.length()) {
                val n = notes.getJSONObject(i)
                dao.insertNote(
                    NoteEntity(
                        title = n.optString("title", ""),
                        content = n.optString("content", ""),
                        folderId = if (n.has("folderId") && !n.isNull("folderId")) n.optLong("folderId") else null,
                        color = n.optLong("color", 0xFFFFFFFF),
                        isFavorite = n.optBoolean("isFavorite", false),
                        isArchived = n.optBoolean("isArchived", false),
                        createdAt = n.optLong("createdAt", System.currentTimeMillis()),
                        updatedAt = n.optLong("updatedAt", System.currentTimeMillis()),
                        reminderTime = if (n.has("reminderTime") && !n.isNull("reminderTime")) n.optLong("reminderTime") else null,
                    )
                )
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBackupFiles(): List<String> {
        return backupDir.listFiles()
            ?.filter { it.name.endsWith(".json") }
            ?.sortedByDescending { it.lastModified() }
            ?.map { it.name }
            ?: emptyList()
    }

    private suspend fun buildBackupJson(): JSONObject {
        val dao = database.noteDao()
        val folderDao = database.folderDao()

        val notesJson = JSONArray()
        dao.getAllNotesOnce().forEach { note ->
            notesJson.put(JSONObject().apply {
                put("id", note.id)
                put("title", note.title)
                put("content", note.content)
                put("folderId", note.folderId ?: JSONObject.NULL)
                put("color", note.color)
                put("isFavorite", note.isFavorite)
                put("isArchived", note.isArchived)
                put("createdAt", note.createdAt)
                put("updatedAt", note.updatedAt)
                put("reminderTime", note.reminderTime ?: JSONObject.NULL)
            })
        }

        val foldersJson = JSONArray()
        folderDao.getAllFoldersOnce().forEach { folder ->
            foldersJson.put(JSONObject().apply {
                put("id", folder.id)
                put("name", folder.name)
                put("color", folder.color)
                put("parentId", folder.parentId ?: JSONObject.NULL)
            })
        }

        return JSONObject().apply {
            put("version", 1)
            put("exportedAt", System.currentTimeMillis())
            put("notes", notesJson)
            put("folders", foldersJson)
        }
    }
}

package com.notesapp.features.backup

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class BackupWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val backupManager: BackupManager,
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return if (backupManager.createBackup().isSuccess) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "auto_backup"
    }
}

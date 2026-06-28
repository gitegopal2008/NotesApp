package com.notesapp.features.reminders

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ReminderReceiver : BroadcastReceiver() {

    @Inject
    lateinit var notificationHelper: ReminderNotificationHelper

    override fun onReceive(context: Context, intent: Intent) {
        val noteId = intent.getLongExtra("note_id", -1L)
        if (noteId == -1L) return

        val title = intent.getStringExtra("note_title") ?: "Note Reminder"
        val content = intent.getStringExtra("note_content") ?: ""

        // Inject is too late for manual injection flow, use Application instance
        val app = context.applicationContext as? androidx.hilt.work.HiltWrapper_WorkerFactory
            ?: return

        notificationHelper.showReminderNotification(noteId, title, content)
    }
}

/**
 * A simpler non-Hilt receiver for reminders that works without Hilt injection.
 * This is used as a fallback since BroadcastReceivers have lifecycle issues with Hilt.
 */
class SimpleReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val noteId = intent.getLongExtra("note_id", -1L)
        val title = intent.getStringExtra("note_title") ?: "Note Reminder"
        val content = intent.getStringExtra("note_content") ?: ""

        val helper = ReminderNotificationHelper(context)
        helper.showReminderNotification(noteId, title, content)
    }
}

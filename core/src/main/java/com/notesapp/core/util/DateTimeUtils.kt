package com.notesapp.core.util

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DateTimeUtils {

    fun formatRelativeTime(timestamp: Long): String {
        val now = System.currentTimeMillis()
        val diff = now - timestamp

        return when {
            diff < 60_000 -> "Just now"
            diff < 3_600_000 -> "${diff / 60_000}m ago"
            diff < 86_400_000 -> "${diff / 3_600_000}h ago"
            isToday(timestamp) -> "Today ${formatTime(timestamp)}"
            isYesterday(timestamp) -> "Yesterday ${formatTime(timestamp)}"
            diff < 604_800_000L -> "${diff / 86_400_000}d ago"
            else -> formatDate(timestamp)
        }
    }

    fun formatDate(timestamp: Long): String {
        val date = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
        return date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    }

    fun formatTime(timestamp: Long): String {
        val time = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalTime()
        return time.format(DateTimeFormatter.ofPattern("hh:mm a"))
    }

    fun formatDateTime(timestamp: Long): String {
        return "${formatDate(timestamp)} ${formatTime(timestamp)}"
    }

    fun isToday(timestamp: Long): Boolean {
        val date = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
        return date == LocalDate.now()
    }

    private fun isYesterday(timestamp: Long): Boolean {
        val date = Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
        return date == LocalDate.now().minusDays(1)
    }
}

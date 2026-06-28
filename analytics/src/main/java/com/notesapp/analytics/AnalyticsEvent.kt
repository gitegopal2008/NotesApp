package com.notesapp.analytics

data class AnalyticsEvent(
    val name: String,
    val params: Map<String, String> = emptyMap()
)

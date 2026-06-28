package com.notesapp.analytics

interface AnalyticsTracker {
    fun logEvent(event: AnalyticsEvent)
    fun logScreenView(screen: AnalyticsScreen)
    fun setUserId(id: String)
    fun setUserProperty(key: String, value: String)
}

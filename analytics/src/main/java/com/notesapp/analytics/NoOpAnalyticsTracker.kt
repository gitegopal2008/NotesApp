package com.notesapp.analytics

import com.notesapp.core.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoOpAnalyticsTracker @Inject constructor(
    private val logger: Logger
) : AnalyticsTracker {

    override fun logEvent(event: AnalyticsEvent) {
        logger.d("Analytics", "Event: ${event.name} | Params: ${event.params}")
    }

    override fun logScreenView(screen: AnalyticsScreen) {
        logger.d("Analytics", "Screen view: ${screen.name}")
    }

    override fun setUserId(id: String) {
        // No-op for v1
    }

    override fun setUserProperty(key: String, value: String) {
        // No-op for v1
    }
}

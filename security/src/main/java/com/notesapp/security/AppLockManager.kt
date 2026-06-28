package com.notesapp.security

import com.notesapp.domain.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppLockManager @Inject constructor(
    private val preferencesRepository: UserPreferencesRepository
) {
    private var isUnlocked: Boolean = false

    fun isAppLockEnabled(): Flow<Boolean> = preferencesRepository.isBiometricEnabled

    fun lockApp() {
        isUnlocked = false
    }

    fun unlockApp() {
        isUnlocked = true
    }

    fun isAppLocked(): Boolean = !isUnlocked
}

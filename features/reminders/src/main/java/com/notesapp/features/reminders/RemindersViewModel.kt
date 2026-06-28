package com.notesapp.features.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.notesapp.domain.usecase.reminders.CancelReminderUseCase
import com.notesapp.domain.usecase.reminders.GetUpcomingRemindersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val getUpcomingRemindersUseCase: GetUpcomingRemindersUseCase,
    private val cancelReminderUseCase: CancelReminderUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RemindersUiState())
    val uiState: StateFlow<RemindersUiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        RemindersUiState()
    )

    init {
        loadReminders()
    }

    fun loadReminders() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            getUpcomingRemindersUseCase().collect { reminders ->
                _uiState.update {
                    it.copy(reminders = reminders, isLoading = false, error = null)
                }
            }
        }
    }

    fun cancelReminder(reminderId: Long) {
        viewModelScope.launch {
            cancelReminderUseCase(reminderId)
        }
    }
}

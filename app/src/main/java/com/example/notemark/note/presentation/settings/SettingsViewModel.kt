package com.example.notemark.note.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notemark.note.domain.NoteMarkDataStore
import com.example.notemark.note.domain.NoteMarkRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val noteMarkRepository: NoteMarkRepository,
    private val noteMarkDataStore: NoteMarkDataStore,
) : ViewModel() {

    private val eventChannel = Channel<SettingsEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: SettingsAction) {
        when (action) {
            SettingsAction.OnBackClick -> {}
            SettingsAction.OnLogOutClick -> onLogOut()
        }
    }

    private fun onLogOut() {
        viewModelScope.launch {
            eventChannel.send(SettingsEvent.OnLogOut)
            noteMarkDataStore.getSettings().firstOrNull()?.refreshToken?.let { token ->
                noteMarkRepository.logoutUser(token)
            }
            noteMarkDataStore.clear()
        }
    }
}
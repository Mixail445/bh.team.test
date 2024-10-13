package com.example.client.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ClientVm @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ClientView.Model())
    val uiState: StateFlow<ClientView.Model> = _uiState.asStateFlow()

    private val _uiLabels = MutableSharedFlow<ClientView.UiLabel>()
    val uiLabels: SharedFlow<ClientView.UiLabel> get() = _uiLabels

    suspend fun onEvent(event: ClientView.Event) {
        when (event) {
            ClientView.Event.OnClickConfig -> handlerClickConfig()
            ClientView.Event.OnClickSave -> TODO()
            ClientView.Event.OnClickStartStop -> handlerClickStartStop()
        }
    }

    private suspend fun handlerClickStartStop() {
        _uiLabels.emit(ClientView.UiLabel.ShowBrowser)
    }

    private var showEditText: Boolean = false

    private fun handlerClickConfig() {
        showEditText = !showEditText
        _uiState.update {
            it.copy(showEditText = showEditText)
        }
    }

}
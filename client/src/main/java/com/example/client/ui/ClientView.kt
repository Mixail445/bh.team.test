package com.example.client.ui

interface ClientView {
    data class Model(
        val ip: String = "",
        val port: String = "",
        val showEditText: Boolean = false
    )

    sealed class Event {
        data object OnClickConfig : Event()
        data object OnClickStartStop : Event()
        data object OnClickSave : Event()
    }
    sealed class UiLabel{
        data object ShowBrowser:UiLabel()
    }
}
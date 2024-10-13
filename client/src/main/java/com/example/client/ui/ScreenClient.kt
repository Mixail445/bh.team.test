package com.example.client.ui

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.client.R
import com.example.client.accessibilityservice.MyAccessibilityService
import com.example.client.utils.PermissionManager
import com.example.common.LaunchAndRepeatWithLifecycle
import com.example.common_ui.ui.BaseButton
import com.example.common_ui.ui.BaseTextField
import kotlinx.coroutines.launch


@Composable
fun ScreenClient() {
    val context = LocalContext.current
    val viewModel = hiltViewModel<ClientVm>()
    val uiState by viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val permissionManager = remember { PermissionManager(context) }

    // Состояние активности сервиса
    var serviceActive by remember { mutableStateOf(permissionManager.isAccessibilityServiceEnabled(MyAccessibilityService::class.java)) }

    fun startService() {
        if (!permissionManager.isAccessibilityServiceEnabled(MyAccessibilityService::class.java)) {
            permissionManager.requestAccessibilityPermission()
            return
        }

        serviceActive = !serviceActive
        val intent = Intent(context, MyAccessibilityService::class.java)

        if (serviceActive) {
            context.startService(intent)
        } else {
            context.stopService(intent)
        }
    }

    fun handleUiLabel(uiLabel: ClientView.UiLabel): Unit =
        when (uiLabel) {
            ClientView.UiLabel.ShowBrowser -> {
                startService()
            }
        }

    LaunchAndRepeatWithLifecycle {
        viewModel.uiLabels.collect { label ->
            handleUiLabel(label)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (uiState.showEditText) {
                BaseTextField(placeholderText = stringResource(R.string.client_text_ip))
                BaseTextField(placeholderText = stringResource(R.string.client_text_port))
                BaseButton(
                    text = stringResource(R.string.client_text_save),
                    onClick = { coroutineScope.launch { viewModel.onEvent(ClientView.Event.OnClickSave) } },
                    modifier = Modifier
                )
            } else {
                BaseButton(stringResource(R.string.client_text_config), onClick = {
                    coroutineScope.launch { viewModel.onEvent(ClientView.Event.OnClickConfig) }
                }, modifier = Modifier)

                BaseButton(stringResource(R.string.client_text_start_stop), onClick = {
                    startService() // Запускаем или останавливаем сервис
                }, modifier = Modifier)
            }
        }
    }
}
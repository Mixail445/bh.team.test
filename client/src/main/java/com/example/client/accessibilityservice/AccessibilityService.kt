package com.example.client.accessibilityservice

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Intent
import android.net.Uri
import android.view.accessibility.AccessibilityEvent

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Обработка событий доступности, если необходимо
    }

    override fun onInterrupt() {
        // Обработка прерываний
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
        configureService()
    }

    private fun configureService() {
        val info = AccessibilityServiceInfo().apply {
            eventTypes =
                AccessibilityEvent.TYPE_VIEW_CLICKED or AccessibilityEvent.TYPE_VIEW_FOCUSED
            feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS
            packageNames =
                arrayOf("com.android.chrome")
        }
        this.serviceInfo = info
    }

    private fun launchGoogle() {
        val intent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=картинки"))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}
package com.example.client.utils

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.text.TextUtils
import android.view.accessibility.AccessibilityManager
import com.example.client.accessibilityservice.MyAccessibilityService

class PermissionManager(private val context: Context) {

    fun isAccessibilityServiceEnabled(service: Class<out AccessibilityService>): Boolean {
        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = Settings.Secure.getString(context.contentResolver, Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES)

        if (enabledServices.isNullOrEmpty()) return false

        val colonSplitter = TextUtils.SimpleStringSplitter(',')
        colonSplitter.setString(enabledServices)

        while (colonSplitter.hasNext()) {
            val componentName = colonSplitter.next()
            if (componentName.equals("${context.packageName}/${service.name}", ignoreCase = true)) {
                return true
            }
        }
        return false
    }

    fun requestAccessibilityPermission() {
        if (!isAccessibilityServiceEnabled(MyAccessibilityService::class.java)) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            context.startActivity(intent)
        }
    }
}
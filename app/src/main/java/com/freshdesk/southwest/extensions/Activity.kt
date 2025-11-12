package com.freshdesk.southwest.extensions

import android.app.Activity
import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.VANILLA_ICE_CREAM
import android.view.WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
import android.view.WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsCompat.Type.ime
import androidx.core.view.WindowInsetsCompat.Type.systemBars

// Applies system bar insets and sets system bar appearance,
fun Activity.applySDK35InsetsListener() {
    if (applicationInfo.targetSdkVersion >= VANILLA_ICE_CREAM && SDK_INT >= VANILLA_ICE_CREAM) {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.isNavigationBarContrastEnforced = true
        val isDarkTheme = (
            resources.configuration.uiMode and UI_MODE_NIGHT_MASK
            ) == UI_MODE_NIGHT_YES
        window.decorView.windowInsetsController?.apply {
            val appearance = if (!isDarkTheme) {
                APPEARANCE_LIGHT_STATUS_BARS or APPEARANCE_LIGHT_NAVIGATION_BARS
            } else {
                0
            }
            setSystemBarsAppearance(appearance, appearance)
        }
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, insets ->
            val systemBars = insets.getInsets(
                systemBars() or ime()
            )
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom
            )
            WindowInsetsCompat.CONSUMED
        }
    }
}
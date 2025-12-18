package com.gurman.myapplication.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.compositionLocalOf

@Immutable
data class AppTheme(
    val isDark: Boolean = false,
    val colors: TestAppColorScheme = if (isDark) {
        darkThemeDark()
    } else {
        lightCrownTheme()
    }
)

val LocalTheme = compositionLocalOf { AppTheme() }

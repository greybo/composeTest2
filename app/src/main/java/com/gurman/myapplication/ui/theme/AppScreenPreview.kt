package com.gurman.myapplication.ui.theme

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator

@Composable
fun AppScreenPreview(
    screen: Screen
) {
    PreviewThemeWrapper {
        Navigator(screen)
    }
}

package com.gurman.myapplication.ui.theme

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Dark Mode",
    group = "Dark Theme",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Light Mode",
    group = "Light Theme",
    showSystemUi = true,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
annotation class ThemePreviews

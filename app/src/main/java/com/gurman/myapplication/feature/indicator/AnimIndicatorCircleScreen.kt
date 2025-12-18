package com.gurman.myapplication.feature.indicator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.gurman.myapplication.ui.theme.AppScreenPreview
import com.gurman.myapplication.ui.theme.ThemePreviews


@ThemePreviews
@Composable
private fun PreviewScreen() = AppScreenPreview(AnimIndicatorCircleScreen())


class AnimIndicatorCircleScreen() : Screen {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp),
//            contentAlignment = Alignment.Center
        ) {
            CircularStatusIndicator(
                currentCredits = 100,
                requiredCredits = 180,
                gapAngle = 60f // Менший розрив
            )

            PlatinumStatusIndicator()
        }
    }
}

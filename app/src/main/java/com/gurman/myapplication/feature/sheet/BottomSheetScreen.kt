package com.gurman.myapplication.feature.sheet

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class BottomSheetScreen : Screen {
    @Composable
    override fun Content() {
//        SimpleBottomSheetScaffoldSample()
        ModalBottomSheetScreen()
    }
}


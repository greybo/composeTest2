package com.gurman.myapplication

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import com.gurman.myapplication.feature.pager.MembershipCardsPagerComponent

class OtherCollectionScreen: Screen {
    @Composable
    override fun Content() {
//        PagerTest2()
        MembershipCardsPagerComponent()
    }
}
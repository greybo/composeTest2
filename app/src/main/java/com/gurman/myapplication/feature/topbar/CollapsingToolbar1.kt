package com.gurman.myapplication.feature.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CollapsingToolbar1() {
    // 1. Create the TopAppBarScrollBehavior
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    // 2. The Scaffold handles the layout and connects the scroll behavior
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
//            TopAppBar()
            // 3. The LargeTopAppBar uses the scroll behavior
            Text("Welcome", fontSize = 14.sp)
            LargeTopAppBar(
                title = {
                    Text("Wallet Melbourne")
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        // 4. A scrollable composable, like LazyColumn, for the main content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            items(50) { index ->
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Item $index", modifier = Modifier.padding(16.dp))
                }
            }
        }
    }
}

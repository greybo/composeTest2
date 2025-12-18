package com.gurman.myapplication.feature.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleBottomSheetScaffoldSample() {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        topBar = { TopAppBar(title = { Text("Test topBar") }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray)) },
        scaffoldState = scaffoldState,
//        sheetPeekHeight = 128.dp,
        sheetTonalElevation = 40.dp,
        sheetShadowElevation = 40.dp,
        sheetMaxWidth = 300.dp,
        sheetContainerColor = Color.LightGray,
        sheetContent = {

            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                val text = remember { mutableStateOf("") }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(128.dp), contentAlignment = Alignment.Center
                ) {
                    Text("Swipe up to expand sheet")
                }
                Text("Sheet content")
                Button(
                    modifier = Modifier.padding(bottom = 64.dp),
                    onClick = { scope.launch { scaffoldState.bottomSheetState.partialExpand() } }
                ) {
                    Text("Click to collapse sheet")
                }
                TextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    textStyle = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.height(50.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Text("Scaffold Content show", modifier = Modifier
                .padding(top = 50.dp)
                .clickable { scope.launch { scaffoldState.bottomSheetState.expand() } })
            Text("Close", modifier = Modifier
                .padding(top = 100.dp)
                .clickable {
                    scope.launch {
                        scaffoldState.bottomSheetState.partialExpand()
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "Test snackbar",
                            actionLabel = "ok",
                            withDismissAction = true,
                            duration = SnackbarDuration.Indefinite
                        )
//                    scaffoldState.bottomSheetState.hide()
                    }
                })
        }
    }
}
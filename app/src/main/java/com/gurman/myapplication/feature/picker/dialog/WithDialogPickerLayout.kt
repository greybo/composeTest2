package com.gurman.myapplication.feature.picker.dialog

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.gurman.myapplication.ui.theme.TestAppThemeWrapper
import timber.log.Timber

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WithDialogPickerLayout() {
    val dialogShow = remember { mutableStateOf(false) }
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        OutlinedButton(onClick = { dialogShow.value = true }) {
            Text(text = "Button")
        }
        PickerTimeBlueDialog(dialogShow, selected = (Pair(28, 0))) { _, _, pair ->
            Timber.e("PickerDate callback: ${pair?.first}, ${pair?.second}")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWithDialogPickerScreen() = TestAppThemeWrapper {
    WithDialogPickerLayout()
}
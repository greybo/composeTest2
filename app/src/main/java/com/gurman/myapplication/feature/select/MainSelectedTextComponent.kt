package com.gurman.myapplication.feature.select

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import timber.log.Timber
import kotlin.math.max
import kotlin.math.min

data class SelectedModel(val text: String, val start: Int, val end: Int)

@Composable
fun MainSelectedTextComponent() {

    val annotatedString = AnnotatedString(
        text = textForScroll,
    )

    val textInput = remember { mutableStateOf(TextFieldValue(annotatedString)) }
    val selectedText: MutableState<String> = remember { mutableStateOf("Test") }
    var selectedModelTemp: SelectedModel? = null
    val selectedModels = remember { mutableStateOf<Set<SelectedModel>>(setOf()) }

    val view = LocalView.current
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current

    val textToolbar = remember {
        AndroidTextToolbar(
            view = view,
            callback = object : TextToolbarMenu {
                override val onCopyRequested: () -> Unit = {
                    selectedModelTemp?.let { temp ->
                        val clipText = clipboard.getText()?.text
                        if (temp.text == clipText) {
                            selectedModels.value = selectedModels.value.plus(temp)
                            val spanStyles = selectedModels.value.map {
                                AnnotatedString.Range(
                                    SpanStyle(
                                        fontWeight = FontWeight.W600,
                                        color = Color.Blue
                                    ),
                                    start = min(it.start, it.end),
                                    end = max(it.start, it.end)
                                )
                            }
                            val annotated = AnnotatedString(
                                text = textForScroll,
                                spanStyles = spanStyles,
                            )
                            textInput.value = textInput.value.copy(annotated)
                        }
//                        Timber.e("TextField Copy4: text=${temp.text}, clipText=${clipText}")

                        selectedModelTemp = null
                    }
                }
            }
        )
    }

    CompositionLocalProvider(LocalTextToolbar provides textToolbar) {
        Column {
            TextField(
                value = textInput.value,
                onValueChange = { newValue ->
                    val selected = newValue.getSelectedText().text
                    val start = newValue.selection.start
                    val end = newValue.selection.end
//                    val clipText = clipboard.getText()?.text
                    if (start != end) {
                        selectedModelTemp = SelectedModel(text = selected, start = start, end = end)
                    } else {
//                        val saveText = selectedModelTemp?.text
//                        Timber.e("TextField reset textToolbar 1")
//                        val saveEnd = selectedModelTemp?.end
                        if (selectedModelTemp?.end != end) {
                            textToolbar.status = TextToolbarStatus.Hidden
                            textToolbar.hide()
                            selectedModelTemp = null
//                            Timber.e("TextField reset textToolbar 2")
                        }
                    }

                    textToolbar.status = if (selectedModelTemp != null)
                        TextToolbarStatus.Shown
                    else TextToolbarStatus.Hidden
//                    val saveText = selectedModelTemp?.text
//                    Timber.e("TextField start=${start}, end=${end}, selected=${selected}, clipText=${clipText}, saveText=${saveText}")
//                    Timber.e("TextField on change: text = $selected, start = $start, end = $end")
                    textInput.value = newValue
                },
                readOnly = true,
                colors = TextFieldDefaults.colors().copy(
                    focusedContainerColor = Color.Transparent, // your background color
                    unfocusedContainerColor = Color.Transparent, // your background color
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { Timber.e("clickable") }
            )

            Text(text = selectedText.value)
        }
    }
}
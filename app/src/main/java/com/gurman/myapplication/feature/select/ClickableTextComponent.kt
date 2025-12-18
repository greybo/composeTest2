package com.gurman.myapplication.feature.select

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.TextToolbarStatus
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ClickableTextComponent() {
//    val showMenu = remember { mutableStateOf(false) }
    val context = LocalContext.current

    var showMenu = false
    val annotatedString = AnnotatedString(
        text = textForScroll,
//        spanStyles = listOf(Range(SpanStyle(fontWeight = FontWeight.W600, color = Color.Blue), 10, 200)),
    )

//    CompositionLocalProvider(
//        LocalTextToolbar provides CustomTextToolbar(
//            LocalView.current,
////            musicViewModel.dictionaryAppList,
//            LocalFocusManager.current,
//            LocalClipboardManager.current
//        ),
//    ) {
    val textToolbar = LocalTextToolbar.current

    SelectionContainer {
        ClickableText(
            text = annotatedString,
            style = TextStyle(
                color = Color.Black,
//            if (currentI == listIndex && musicViewModel.autoHighLight.value) {
//                           Color.Blue
//                       } else {
//                           MaterialTheme.colorScheme.onBackground
//                       },
                fontSize = 16.sp,
//            textAlign = musicViewModel.textAlign.value,
//            lineHeight = (fontSize * 1.5).sp,
//            textIndent = if (musicViewModel.textAlign.value == TextAlign.Justify || musicViewModel.textAlign.value == TextAlign.Left) {
//                TextIndent(fontSize.sp * 2)
//            } else {
//                TextIndent.None
//            }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) { offset ->
            Toast.makeText(context, "offset $offset", Toast.LENGTH_SHORT).show()
//            textToolbar.showMenu(
//                rect = Rect.Zero,
//                onCopyRequested = {
//                    Toast.makeText(context, "Copy3", Toast.LENGTH_SHORT).show()
//                },
//                onPasteRequested = {
//                    Toast.makeText(context, "Paste3", Toast.LENGTH_SHORT).show()
//
//                },
//                onCutRequested = {
//                    Toast.makeText(context, "Cut3", Toast.LENGTH_SHORT).show()
//
//                },
//                onSelectAllRequested = {
//                    Toast.makeText(context, "Select3", Toast.LENGTH_SHORT).show()
//
//                }
//            )
            if (textToolbar.status == TextToolbarStatus.Shown) {
                textToolbar.hide()
//            focusManager.clearFocus()
            } else if (showMenu) {
                showMenu = false
            } else {
                val annotations = annotatedString.getStringAnnotations(offset, offset)
                annotations.firstOrNull()?.let { itemAnnotations ->
                    if (itemAnnotations.tag.startsWith("word")) {
//                    selectedTag = "$listIndex ${itemAnnotations.tag}"
//                    word = itemAnnotations.item
                        showMenu = true
                    }
                }
            }

        }
//        }
    }
}


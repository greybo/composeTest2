package com.gurman.myapplication.feature.select

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import timber.log.Timber

@Composable
fun BaseTextComponent() {
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
        BaseTextCompose(
            annotatedString = annotatedString,
            onTextLayout = {
                Timber.e("ClickOnText onTextLayout ${it.layoutInput.text}")
                Toast.makeText(context, "result onTextLayout  ${it}", Toast.LENGTH_SHORT).show()
            },
            onClick = {
                Timber.e("ClickOnText onClick ${it}")
                Toast.makeText(context, "result onClick ${it}", Toast.LENGTH_SHORT).show()
            }
        )


//        BasicText(
//            text = annotatedString,
//            style = TextStyle(
//                color = Color.Black,
//                fontSize = 16.sp,
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            onTextLayout = { result ->
//                Timber.e("ClickOnText ${result}")
//
//                Toast.makeText(context, "result ${result}", Toast.LENGTH_SHORT).show()
//
//                if (textToolbar.status == TextToolbarStatus.Shown) {
//                    textToolbar.hide()
//                } else if (showMenu) {
//                    showMenu = false
//                } else {
//                    val annotations = annotatedString.getStringAnnotations(10, 20)
//                    annotations.firstOrNull()?.let { itemAnnotations ->
//                        if (itemAnnotations.tag.startsWith("word")) {
////                    selectedTag = "$listIndex ${itemAnnotations.tag}"
////                    word = itemAnnotations.item
//                            showMenu = true
//                        }
//                    }
//                }
//            }
//        )
    }
}

@Composable
fun BaseTextCompose(annotatedString: AnnotatedString, onTextLayout: (TextLayoutResult) -> Unit = {}, onClick: (Int) -> Unit) {
    val textToolbar = LocalTextToolbar.current
    val layoutResult = remember { mutableStateOf<TextLayoutResult?>(null) }
    val pressIndicator = Modifier.pointerInput(onClick) {
        detectTapGestures { pos ->
            layoutResult.value?.let { layoutResult ->
                onClick(layoutResult.getOffsetForPosition(pos))
            }
        }
    }

    BasicText(
        text = annotatedString,
        modifier = Modifier.then(pressIndicator),
//        style = style,
//        softWrap = softWrap,
//        overflow = overflow,
//        maxLines = maxLines,
        onTextLayout = {
            layoutResult.value = it
            onTextLayout(it)
        }
    )
}


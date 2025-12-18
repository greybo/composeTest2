package com.gurman.myapplication.feature.select

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalTextToolbar
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.AnnotatedString.Range
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink

//textForScroll
@Composable
fun SelectableTextComponent() {
    val uriHandler = LocalUriHandler.current
    val localTextToolbar = LocalTextToolbar.current
    val context = LocalContext.current

    val annotated2 = AnnotatedString(
        text = textForScroll,
        spanStyles = listOf(Range(SpanStyle(fontWeight = FontWeight.W600, color = Color.Blue), 10, 200)),
    )




    SelectionContainer {
        Column {
            Text(annotated2)


            Text(
                buildAnnotatedString {
                    append("Go to the ")
                    withLink(
                        LinkAnnotation.Url(
                            "https://developer.android.com/",
                            TextLinkStyles(style = SpanStyle(color = Color.Blue))
                        )
                    ) {
                        append("Android Developers ")
                    }
                    append("website, and check out the ")
                    withLink(
                        LinkAnnotation.Url(
                            "https://developer.android.com/jetpack/compose",
                            TextLinkStyles(style = SpanStyle(color = Color.Green))
                        )
                    ) {
                        append("Compose guidance")
                    }
                    append(".")
                }
            )

            Text(
                buildAnnotatedString {
                    append("Build better apps faster with ")
                    val link = LinkAnnotation.Url(
                        "https://developer.android.com/jetpack/compose",
                        TextLinkStyles(SpanStyle(color = Color.Blue))
                    ) {
                        val url = (it as LinkAnnotation.Url).url
                        // log some metrics
                        uriHandler.openUri(url)
                    }
                    withLink(link) { append("Jetpack Compose") }
                }
            )
        }


//        localTextToolbar.showMenu(
//            rect = Rect.Zero,
//            onCopyRequested = {
//                Toast.makeText(context, "Copy", Toast.LENGTH_LONG).show()
//            },
//            onPasteRequested = {
//                Toast.makeText(context, "Paste", Toast.LENGTH_LONG).show()
//
//            },
//            onCutRequested = {
//                Toast.makeText(context, "Cut", Toast.LENGTH_LONG).show()
//
//            },
//            onSelectAllRequested = {
//                Toast.makeText(context, "Select", Toast.LENGTH_LONG).show()
//
//            }
//        )
    }
}
package com.gurman.myapplication.feature.sheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gurman.myapplication.ui.theme.LocalTheme
import com.gurman.myapplication.ui.theme.TestAppColorScheme
import kotlinx.coroutines.launch


@Suppress("ktlint:standard:function-naming")
@Composable
fun ModalBottomSheetScreen() {
    val openSheetState = remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val colors = LocalTheme.current.colors
    Scaffold() {
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { openSheetState.value = true }) { Text("Open bottomSheet") }
            ModalBottomSheetComponent(openSheetState)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Suppress("ktlint:standard:function-naming")
@Composable
fun ModalBottomSheetComponent(
    openSheetState: MutableState<Boolean> = mutableStateOf(false),
//    sheetContent: @Composable ColumnScope.() -> Unit,
) {
    val skipPartiallyExpanded by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)
    val scope = rememberCoroutineScope()
    val colors = LocalTheme.current.colors

    LaunchedEffect(openSheetState.value) {
        if (!openSheetState.value)
            scope.launch {
                bottomSheetState.hide()
            }
    }

    if (openSheetState.value) {
        ModalBottomSheet(
            onDismissRequest = { openSheetState.value = false },
            dragHandle = {},
            sheetState = bottomSheetState,
            content = {
                Box(modifier = Modifier.wrapContentHeight()) {
                    BottomSheetContent() { openSheetState.value = false }
                }
            },
        )
    }
}


@Preview
@Composable
fun BottomSheetContent(onCancel: () -> Unit = {}) {


    val title: String = rewardsYourSCRenewsHeader
    val text: String = rewardsYourSCRenewsContent
    Box(/*contentAlignment = Alignment.BottomCenter*/) {
        Column() {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
            )

            Column(
                modifier = Modifier
//                .fillMaxHeight(/*0.4f*/)
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = AnnotatedString.fromHtml(htmlString = text.trimIndent()),
                )
            }
        }
        BottomButton(modifier = Modifier/*.align(Alignment.BottomCenter)*/, onClick = onCancel)
    }
}

@Composable
private fun BoxScope.BottomButton(modifier: Modifier, colors: TestAppColorScheme = LocalTheme.current.colors, onClick: () -> Unit) {
    Column(modifier = Modifier.Companion.align(Alignment.BottomCenter)) {
        HorizontalDivider(
            thickness = 1.dp,
            modifier = modifier
                .padding(horizontal = 50.dp)
        )

        TextButton(
            modifier = Modifier.padding(horizontal = 36.dp),
            onClick = onClick
        ) {
            Text(
                text = "Cancel",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                color = colors.semanticBlue,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

val rewardsYourSCRenewsHeader
    get() = "Renews Header"

val rewardsYourSCRenewsContent
    get() = "<p> <b>Lifestyle points</b><br>There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form. <br><br>Hotels<br>If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text.<br><br><b>Games</b><br>All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.<br></p>"



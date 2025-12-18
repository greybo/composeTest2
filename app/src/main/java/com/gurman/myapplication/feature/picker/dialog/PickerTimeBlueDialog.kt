package com.gurman.myapplication.feature.picker.dialog

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.gurman.myapplication.ui.theme.LocalTheme
import com.gurman.myapplication.ui.theme.TestAppTextStyles.BodyRegular
import com.gurman.myapplication.ui.theme.TestAppTextStyles.HeadlineSemiBold
import com.gurman.myapplication.ui.theme.TestAppThemeWrapper
import kotlinx.coroutines.launch
import timber.log.Timber

@SuppressLint("SuspiciousIndentation")
@Composable
fun PickerTimeBlueDialog(
    isShow: MutableState<Boolean> = mutableStateOf(true),
    selected: Pair<Int, Int> = Pair(28, 0),
    positiveName: String = "Ok",
    negativeName: String = "Cancel",
    onClick: (Boolean, String, Pair<Int, Int>?) -> Unit,
) {
    if (!isShow.value) return

    val colors = LocalTheme.current.colors
    val minuteEnabled = remember { mutableStateOf(selected.first != 28) }
    var isButtonEnable by remember { mutableStateOf(true) }
    var selectH by remember { mutableIntStateOf(selected.first) }
    var selectM by remember { mutableIntStateOf(selected.second) }

    val dismiss: (Boolean, String) -> Unit = { action, name ->
        onClick(action, name, Pair(selectH, selectM))
        isShow.value = false
    }

    Dialog(
        properties = DialogProperties(dismissOnClickOutside = false),
        onDismissRequest = {},
        content = {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(modifier = Modifier.background(color = colors.dialogBackground)) {
                    Spacer(Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        VerticalPagerComp(29, selected = selectH, "h") {
                            selectH = it
                            minuteEnabled.value = it != 28
                            Timber.e("PickerDate minuteEnabled=${minuteEnabled.value}")
                        }
                        Spacer(Modifier.width(30.dp))
                        VerticalPagerComp(
                            60,
                            selected = selectM,
                            suffix = "min",
                            enabled = minuteEnabled.value
                        ) {
                            selectM = it
                        }
                    }

                    isButtonEnable = !(selectM == 0 && selectH == 0)
                    Timber.e("PickerDate isButtonEnable=${isButtonEnable}, time=${selectH.toString() + "h  " + selectM.toString() + "min"}")
                    val colorButton =
                        if (isButtonEnable) colors.semanticBlue else colors.textDisabled

                    Column(modifier = Modifier.clickable(enabled = isButtonEnable) {
                        dismiss(true, positiveName)
                    }) {
                        Spacer(Modifier.height(24.dp))
                        HorizontalDivider(color = colors.dialogDivider, thickness = 0.33.dp)
                        Text(
                            text = positiveName,
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = HeadlineSemiBold.copy(color = colorButton)
                        )
                    }

                    Column(modifier = Modifier.clickable { dismiss(false, negativeName) }) {
                        HorizontalDivider(color = colors.dialogDivider, thickness = 0.33.dp)
                        Text(
                            text = negativeName,
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 16.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = BodyRegular.copy(color = colors.semanticBlue)
                        )
                    }
                }
            }
        }
    )
}

@SuppressLint("AutoboxingStateValueProperty")
@Composable
private fun VerticalPagerComp(
    count: Int,
    selected: Int,
    suffix: String,
    enabled: Boolean = true,
    callback: (Int) -> Unit
) {

    val maxCount = count * 200
    val selectCenter = selected + count * 100
    val pagerState = rememberPagerState(pageCount = { maxCount })
    val colors = LocalTheme.current.colors
    val scope = rememberCoroutineScope()
    val colorText = if (enabled) colors.textCtaTertiary else colors.textDisabled

    val heightCell = 70.dp
    val widthCell = 100.dp

    val fling = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(10)
    )

    LaunchedEffect(Unit) {
        Timber.e("PickerDate LaunchedEffect scrollToPag first scroll $suffix")
        pagerState.scrollToPage(selectCenter)
    }

    Column(
        modifier = Modifier.width(widthCell)
    ) {
        IconChevron(Icons.Default.KeyboardArrowUp, enabled = enabled) {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
        }
        VerticalPager(
            state = pagerState,
            modifier = Modifier.height(heightCell),
            userScrollEnabled = enabled,
            flingBehavior = fling,
        ) { position ->

            val page = position % count
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$page $suffix",
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    style = HeadlineSemiBold,
                    color = colorText
                )
            }
        }
        IconChevron(Icons.Default.KeyboardArrowDown, enabled = enabled) {
            scope.launch {
                pagerState.animateScrollToPage(pagerState.currentPage - 1)
            }
        }
    }

    LaunchedEffect(enabled) {
        if (!enabled) {
            Timber.e("PickerDate LaunchedEffect enabled=$enabled, selected=$selected")
            pagerState.scrollToPage(count * 100)
        }
    }
    val result = pagerState.currentPage % count
    Timber.e("PickerDate result callback=${result} $suffix")
    callback(result)
}

@Composable
private fun ColumnScope.IconChevron(
    imageVector: ImageVector,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    IconButton(
        onClick = { onClick() },
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.CenterHorizontally)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = "",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewPickerDialogCustom() = TestAppThemeWrapper {
    PickerTimeBlueDialog(mutableStateOf(true)) { _, _, pair -> }
}

@Preview
@Composable
fun PreviewVerticalPagerComp() = TestAppThemeWrapper {
    VerticalPagerComp(count = 14, 0, "min", enabled = false) {}
}
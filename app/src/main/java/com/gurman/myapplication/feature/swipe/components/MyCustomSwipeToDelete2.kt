package com.gurman.myapplication.feature.swipe.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import timber.log.Timber

@Composable
fun MyCustomSwipeToDelete2(
    onDelete: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    // 1. Визначаємо стан свайпа
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { swipeValue ->
            Timber.w("positionalThreshold ${swipeValue.name}")
            when (swipeValue) {
                SwipeToDismissBoxValue.Settled -> true
                SwipeToDismissBoxValue.EndToStart -> true
                SwipeToDismissBoxValue.StartToEnd -> false
            }
        },

        positionalThreshold = { totalWidth -> totalWidth * 0.5f } // Зупинка на 50% (половині)
    )
    LaunchedEffect(dismissState.progress) {
//        Timber.w("dismissState.currentValue ${dismissState.progress}")
//        dismissState.snapTo(SwipeToDismissBoxValue.EndToStart)
    }
    // 2. Визначаємо, чи елемент свайпнуто достатньо (progress > 0.5f)
    val isSwipedHalfway = dismissState.progress > 0.5f

    // 3. Автоматично фіксуємо елемент на половині, якщо свайпнуто достатньо
    LaunchedEffect(isSwipedHalfway, dismissState.currentValue) {
        if (isSwipedHalfway && dismissState.currentValue == SwipeToDismissBoxValue.Settled) {
//            dismissState.snapTo(SwipeToDismissBoxValue.EndToStart)
        }
    }

    // 4. Основний контейнер зі свайпом
    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = false, // Вимкнути свайп в інший бік
        backgroundContent = {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable() { onDelete() } // Клік на іконку видалення
                        .align(Alignment.CenterVertically)
                )
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "Undo",
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clickable { onDelete() } // Клік на іконку видалення
                        .align(Alignment.CenterVertically)
                )
            }
        },
        content = content
    )
}
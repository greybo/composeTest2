package com.gurman.myapplication.feature.topbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min

@Composable
fun CollapsingToolbar2() {
    val scrollState = rememberLazyListState()

    // Прогрес скролінгу від 0 (вгорі) до 1 (повністю згорнуто)
    val scrollProgress = remember {
        derivedStateOf {
            val offset = scrollState.firstVisibleItemScrollOffset.toFloat()
            min(offset / 150f, 1f)
        }
    }
//    Scaffold(
//        topBar = {
//            AnimatedHeader(scrollProgress = scrollProgress.value)
//        }
//    ) {
//        Box(modifier = Modifier
//            .fillMaxSize()
//            .padding(it)) {
//            // Список з подіями
//            LazyColumn(
//                state = scrollState,
//                modifier = Modifier.fillMaxSize(),
//                contentPadding = PaddingValues(top = 120.dp)
//            ) {
//                items(30) { index ->
//                    EventCard(index = index)
//                }
//            }
//        }
//    }
    Box(modifier = Modifier.fillMaxSize()) {
        // Список з подіями
        LazyColumn(
            state = scrollState,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 120.dp)
        ) {
            items(30) { index ->
                EventCard(index = index)
            }
        }
        Spacer(Modifier.height(24.dp))
        // Згортаємий header
        AnimatedHeader(scrollProgress = scrollProgress.value)
    }
}

@Composable
fun AnimatedHeader(scrollProgress: Float) {
    // Інтерполяція розміру header
    val headerHeight = 120.dp - (64.dp * scrollProgress)

    // Розмір тексту
    val titleFontSize = with(LocalDensity.current) { (22.sp.toPx() - (6.sp.toPx() * scrollProgress)).toSp() }

    // Прозорість підзаголовка
    val subtitleAlpha = 1f - scrollProgress

    // Вертикальне зміщення тексту
    val textVerticalPadding = 16.dp + (16.dp * scrollProgress)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(headerHeight),
        color = Color.Black,
        shadowElevation = if (scrollProgress > 0.1f) 8.dp else 0.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Іконки
            Row(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Notification з бейджем
                Box {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .offset(x = 12.dp, y = (-2).dp)
                            .background(Color(0xFF2196F3), shape = MaterialTheme.shapes.small)
                    )
                }

                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Анімований текст
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                        start = 16.dp,
                        bottom = textVerticalPadding
                    )
            ) {
                // "What's on at" - зникає
                if (scrollProgress < 0.9f) {
                    Text(
                        text = "What's on at",
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.7f),
                        modifier = Modifier
                            .graphicsLayer {
                                alpha = subtitleAlpha
                                translationY = -scrollProgress * 20f
                            }
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                }

                // "Crown Melbourne" з стрілкою
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.graphicsLayer {
                        translationY = -scrollProgress * 30f
                    }
                ) {
                    Text(
                        text = "Crown Melbourne",
                        fontSize = titleFontSize,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    // Стрілка зникає при скролі
                    if (scrollProgress < 0.6f) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Expand",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .size(24.dp)
                                .graphicsLayer {
                                    alpha = 1f - (scrollProgress * 1.5f)
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EventCard(index: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E1E1E)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Event ${index + 1}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Description of the event at Crown Melbourne",
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.7f)
            )
        }
    }
}
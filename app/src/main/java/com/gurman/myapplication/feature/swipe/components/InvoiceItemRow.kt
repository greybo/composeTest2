package com.gurman.myapplication.feature.swipe.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InvoiceItemRow(
    title: String,
    userEmail: String?,
    date: String?,
    passed: Boolean,
    waitToSave: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        waitToSave -> Color(0xFFE5F1FF) // Light blue for waitToSave
        passed -> Color(0xFFE6FFE6) // Light green for passed
        else -> MaterialTheme.colorScheme.surface
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable(onClick = onClick),
//        backgroundColor = backgroundColor,
//        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = userEmail ?: "",
                style = TextStyle(fontSize = 14.sp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = date ?: "",
                style = TextStyle(fontSize = 12.sp),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )

            if (waitToSave) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Очікує збереження",
                    style = TextStyle(fontSize = 12.sp),
                    color = Color.Blue
                )
            }
        }
    }
}
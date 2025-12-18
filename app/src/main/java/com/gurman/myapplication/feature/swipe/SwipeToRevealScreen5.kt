package com.gurman.myapplication.feature.swipe

import android.widget.Toast
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import cafe.adriel.voyager.core.screen.Screen
import com.gurman.myapplication.feature.swipe.components.InvoiceItemRow
import com.gurman.myapplication.feature.swipe.components.MySwipeToDismiss5

class SwipeToRevealScreen5 : Screen {
    @Composable
    override fun Content() {

        val context = LocalContext.current
        val itemRow = remember { mutableStateOf(invoiceRows) }

        fun delete(key: String) {
            Toast.makeText(context, "onDelete key=$key", Toast.LENGTH_SHORT).show()
            itemRow.value = itemRow.value.filter { it.title != key }
        }

        LazyColumn(modifier = Modifier) {
            val list = itemRow.value
            item {
                Text("SwipeToRevealScreen5")
            }
            items(count = list.size, key = { list[it].hashCode() }) {
                val item = list[it]

                MySwipeToDismiss5(
                    onDelete = { delete(item.title) },
                    content = {
                        InvoiceItemRow(
                            title = item.title,
                            userEmail = item.userEmail,
                            date = item.date,
                            passed = item.passed,
                            waitToSave = item.waitToSave,
                            onClick = {
                                if (it) {
                                    Toast.makeText(context, "OnClick 2", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                )

            }
        }

    }
}

val invoiceRows = listOf(
    InvoiceMailItemRow(title = "title 1"),
    InvoiceMailItemRow(title = "title 2"),
    InvoiceMailItemRow(title = "title 3"),
    InvoiceMailItemRow(title = "title 4"),
    InvoiceMailItemRow(title = "title 5"),
    InvoiceMailItemRow(title = "title 6"),
    InvoiceMailItemRow(title = "title 7"),
    InvoiceMailItemRow(title = "title 8"),
)

data class InvoiceMailItemRow(
    val title: String = "title",
    val date: String? = "data",
    val userEmail: String? = "userEmail",
    val id: String? = "id",
    val passed: Boolean = true,
    val fileName: String = "fileName",
    val waitToSave: Boolean = true
)
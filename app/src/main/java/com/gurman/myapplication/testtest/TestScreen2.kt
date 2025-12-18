package com.gurman.myapplication.testtest

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import com.gurman.myapplication.utils.Datum
import com.gurman.myapplication.utils.PaginatedResponse
import com.gurman.myapplication.utils.paginate
import kotlinx.coroutines.delay

class TestScreen2 : Screen {


    @Composable
    override fun Content() {
        val scope = rememberCoroutineScope()

        Scaffold { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Text("Text screen 2 !!")
                LaunchedEffect(Unit) {
                    paginate(
                        maxRequests = 9,
                        fetchPage = {
                            delay(1000)

                            if (it == 3) {
                                Result.failure(Throwable("test on page 3"))
                            }else
                            Result.success(
                                PaginatedResponse(
                                    item = "Items string",
                                    Datum(
                                        currentPage = it,
                                        pageCount = 25,
                                        itemsCount = 20
                                    )
                                ),
                            )
                        },
                        transform = { it }
                    )
                }
            }
        }
    }


}
package com.gurman.myapplication.utils

import timber.log.Timber

suspend fun <T, R> paginate(
//    initialPage: Int = 1,
    maxRequests: Int,
    fetchPage: suspend (page: Int) -> Result<PaginatedResponse<T>>,
    transform: (T) -> R,
    onError: (Throwable, Int) -> Unit = { e, i -> Timber.e(e, "error in page-$i") },
    onProgress: (currentPage: Int, totalPages: Int, totalItems: Int) -> Unit = { cp, tp, totalItems -> Timber.w("progress: $cp, $tp, $totalItems") }
): List<R> {
    val results = mutableListOf<R>()
    var pageCount = 1
    var currentPage = 0 // Start from 0-based index
    onProgress(currentPage, pageCount, 0)
    while (currentPage < pageCount) {
//        supervisorScope {
            try {
                currentPage++
                val response = fetchPage(currentPage).getOrElse { error ->
                    onError(error, currentPage)
                    return emptyList()
                }

                pageCount = response.datum.pageCount
                results.add(transform(response.item))
                onProgress(currentPage, pageCount, response.datum.itemsCount.toInt())

                if (currentPage >= maxRequests) {
                    Timber.w("end page: $currentPage")
                    currentPage = pageCount // Exit loop
                }
            } catch (e: Exception) {
                onError(e, currentPage)
            }
//        }
    }

    return results
}

data class PaginatedResponse<T>(
    val item: T,
    val datum: Datum,
)

data class Datum(
    var currentPage: Int /*= 0*/,
    var pageCount: Int /*= 0*/,
//    var perPage: Int = 0,
    var itemsCount: Int /*= 0*/,
)

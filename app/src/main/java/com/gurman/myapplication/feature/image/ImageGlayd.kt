//package com.gurman.myapplication.components.image
//
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.lazy.rememberLazyListState
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import com.bumptech.glide.RequestManager
//import com.bumptech.glide.integration.compose.GlideImage
//
//@Composable
//fun DeviceMedia2(mediaStoreData: List<MediaStoreData>) {
//    val state = rememberLazyListState()
//    LazyRow(state = state) {
//        items(mediaStoreData) { mediaStoreItem ->
//            // Uses GlideImage to display a MediaStoreData object
//            MediaStoreView(mediaStoreItem, requestManager, Modifier.fillParentMaxSize())
//        }
//    }
//
////    GlideLazyListPreloader(
////        state = state,
////        data = mediaStoreData,
////        size = THUMBNAIL_SIZE,
////        numberOfItemsToPreload = 15,
////        fixedVisibleItemCount = 2,
////    ) { item, requestBuilder ->
////        requestBuilder.load(item.uri).signature(item.signature())
////    }
//}
//
//@Composable
//fun MediaStoreView(item: MediaStoreData, requestManager: RequestManager, modifier: Modifier) {
//    val signature = item.signature()
//
//    GlideImage(
//        model = item.uri,
//        contentDescription = item.displayName,
//        modifier = modifier,
//    ) {
//        it
//            // This thumbnail request exactly matches the request in GlideLazyListPreloader
//            // so that the preloaded image can be used here and display more quickly than
//            // the primary request.
//            .thumbnail(
//                requestManager
//                    .asDrawable()
//                    .load(item.uri)
//                    .signature(signature)
//                    .override(THUMBNAIL_DIMENSION)
//            )
//            .signature(signature)
//    }
//}
package com.gurman.myapplication.feature.image

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.sp
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.rememberGlidePreloadingData
import com.bumptech.glide.load.Key
import com.bumptech.glide.signature.MediaStoreSignature


val THUMBNAIL_DIMENSION = 50
val THUMBNAIL_SIZE = Size(THUMBNAIL_DIMENSION.toFloat(), THUMBNAIL_DIMENSION.toFloat())


@Composable
fun DeviceMedia(mediaStoreData: List<MediaStoreData>) {
    val requestBuilderTransform =
        { item: MediaStoreData, requestBuilder: RequestBuilder<Drawable> ->
            requestBuilder.load(item.uri).signature(item.signature())
        }

    val preloadingData =
        rememberGlidePreloadingData(
            mediaStoreData,
            THUMBNAIL_SIZE,
            requestBuilderTransform = requestBuilderTransform,
        )

    Box( modifier = Modifier.fillMaxSize()) {
        Text(text = "Images", fontSize = 25.sp)

        val (mediaStoreItem, preloadRequestBuilder) = preloadingData[0]
        MediaStoreView(mediaStoreItem, preloadRequestBuilder, Modifier)
    }
//    LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.size(100.dp)) {
//        items(preloadingData.size) { index ->
//
//            val (mediaStoreItem, preloadRequestBuilder) = preloadingData[index]
//
//            MediaStoreView(mediaStoreItem, preloadRequestBuilder, Modifier.fillParentMaxSize())
//        }
//    }
}

//    fun MediaStoreData.signature() = MediaStoreSignature(mimeType, dateModified, orientation)
fun MediaStoreData.signature() = MediaStoreSignature("jpeg", 1233311, 0)

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MediaStoreView(
    item: MediaStoreData,
    preloadRequestBuilder: RequestBuilder<Drawable>,
    modifier: Modifier,
) = GlideImage(model = item.uri, contentDescription = item.displayName, modifier = modifier) {
    it.thumbnail(preloadRequestBuilder)
        .signature(MediaStoreSignature("jpeg", 1233311, 0))
//        .signature(item.signature())
}


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MediaStoreView3(item: MediaStoreData, requestManager: RequestManager, modifier: Modifier = Modifier) {
//    val signature = item.signature()
    val signature = MediaStoreSignature("jpeg", 1233311, 0)
    val THUMBNAIL_DIMENSION = 300
    GlideImage(
        model = item.uri,
        contentDescription = item.displayName,
        modifier = modifier,
    ) {

        it
            // This thumbnail request exactly matches the request in GlideLazyListPreloader
            // so that the preloaded image can be used here and display more quickly than
            // the primary request.
            .thumbnail(
                requestManager
                    .asDrawable()
                    .load(item.uri)
                    .signature(signature)
                    .override(THUMBNAIL_DIMENSION)
            )
            .signature(signature)
    }
}

class MediaStoreData(
    val uri: String? = "https://picsum.photos/id/870/536/354?grayscale&blur=2",
    val displayName: String? = "Image"
) {
    //    val uri: String? = null
//    val displayName: String? = null
    fun signature() = Key { MediaStoreSignature("jpeg", 1233311, 0) }
}

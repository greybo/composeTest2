package com.gurman.myapplication.feature.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.compose.rememberConstraintsSizeResolver
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.gurman.myapplication.R
import com.gurman.myapplication.ui.theme.PreviewThemeWrapper
import timber.log.Timber

@Composable
fun ImageCoilExample() {

    val painter = rememberAsyncImagePainter("https://picsum.photos/id/870/536/354?grayscale&blur=2")
    val state by painter.state.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Image COIL 5", fontSize = 25.sp)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://picsum.photos/id/874/536/354?grayscale&blur=2")
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.description),
            error = painterResource(R.drawable.ic_launcher_foreground),
            onLoading = {
                Timber.e("ImageCoilExample on loading")
            },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .height(150.dp),
        )

        Text(text = "Image COIL 4", fontSize = 25.sp)
        Timber.e("ImageCoilExample state $state")
        when (state) {
            is AsyncImagePainter.State.Empty,
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator()
            }

            is AsyncImagePainter.State.Success -> {
                Image(
                    painter = painter,
                    contentDescription = stringResource(R.string.description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .height(150.dp),
                )
            }

            is AsyncImagePainter.State.Error -> {
                // Show some error UI.
                Timber.e("ImageCoilExample 4 state Error ${(state as AsyncImagePainter.State.Error).result.throwable.message}")

            }
        }


        Text(text = "Image COIL3 - SubcomposeAsyncImage", fontSize = 25.sp)
        SubcomposeAsyncImage(
            modifier = Modifier.height(150.dp),
            model = "https://picsum.photos/id/870/536/354?grayscale&blur=2",
            loading = {

                Text(text = "LOADING", fontSize = 25.sp, color = Color.Blue)

                CircularProgressIndicator(modifier = Modifier.size(100.dp))
            },
            contentDescription = stringResource(R.string.description),
        )

        Text(text = "Image COIL 2", fontSize = 25.sp)
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://picsum.photos/id/870/536/354?grayscale&blur=2")
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_launcher_foreground),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = stringResource(R.string.description),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CircleShape)
                .background(Color.Cyan),
        )
        Text(text = "Image COIL 1", fontSize = 25.sp)
        val sizeResolver = rememberConstraintsSizeResolver()
        val painter1 = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data("https://picsum.photos/id/870/536/354?grayscale&blur=2")
                .size(sizeResolver)
                .build(),
        )

        Image(
            painter = painter1,
            contentDescription = null,
            modifier = Modifier.then(sizeResolver),
        )
    }
}

@Preview
@Composable
fun PreviewImageCoil() {
    PreviewThemeWrapper {
        ImageCoilExample()
    }
}
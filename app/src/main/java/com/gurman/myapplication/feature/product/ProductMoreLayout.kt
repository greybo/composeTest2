package com.gurman.myapplication.feature.product

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.gurman.myapplication.feature.product.data.ProductItem
import com.gurman.myapplication.feature.product.data.productItemFake

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductMoreLayout(productItem: ProductItem = productItemFake, onClick: () -> Unit = {}) {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Product Details") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            ProductName(name = productItem.name)
            Spacer(modifier = Modifier.height(8.dp))
            ProductCount(count = productItem.quantityInStock)
            Spacer(modifier = Modifier.height(8.dp))

            ProductAvailability(available = productItem.available)
            ProductImage(imageUrl = productItem.imageUrl)
            Spacer(modifier = Modifier.height(16.dp))
            ProductPrice(price = productItem.price, currency = productItem.currency)
            Spacer(modifier = Modifier.height(16.dp))
            ProductDetails(
                vendor = productItem.vendor,
                vendorCode = productItem.vendorCode,
                barcode = productItem.barcode,
                article = productItem.article
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProductDescription(description = productItem.description, onClick = onClick)

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProductImage(imageUrl: String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build()
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
        contentAlignment = Alignment.Center
    ) {

        when (painter.state.value) {
            is AsyncImagePainter.State.Loading -> {
                CircularProgressIndicator(/*color= Color.Blue*/)
            }

            is AsyncImagePainter.State.Error -> {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Error loading image",
                    tint = Color.Red,
                    modifier = Modifier.size(64.dp)
                )
            }

            else -> {
                Image(
                    painter = painter,
                    contentDescription = "Product image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun ProductAvailability(available: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Text(
            text = if (available) "In stock" else "Out of stock",
            color = if (available) Color.Green else Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun ProductName(name: String) {
    Text(
        text = name,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun ProductPrice(price: Int, currency: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Price:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "$price $currency",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ProductCount(count: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Кількість:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 8.dp)
        )
        Text(
            text = "$count шт",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun ProductDetails(vendor: String, vendorCode: String, barcode: String, article: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Details",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        DetailRow(title = "Vendor:", value = vendor)
        DetailRow(title = "Vendor Code:", value = vendorCode)
        DetailRow(title = "Barcode:", value = barcode)
        DetailRow(title = "Article:", value = article)
    }
}

@Composable
fun DetailRow(title: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.width(120.dp)
        )
        Text(text = value)
    }
}

@Composable
fun ProductDescription(description: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Description",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clickable { onClick() }
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyMedium,
            lineHeight = 20.sp
        )
    }
}


package com.gurman.myapplication.feature.product

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gurman.myapplication.feature.product.data.ProductItem
import com.gurman.myapplication.feature.product.data.productItemFake

@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductHolder(
    item: ProductItem = productItemFake,
    moreDetails: Boolean = false,
    onEditMore: (() -> Unit)? = null,
    onPlace: (() -> Unit) = {},
    onAmount: () -> Unit = {}
) {

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Column {
            Spacer(modifier = Modifier.height(16.dp))
            ProductName(
                name = item.name,
                onClick = onEditMore
            )
            HorizontalDivider(Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
            DetailsRow2(fieldName = "Barcode:", value = item.barcode)
            if (moreDetails) {
                DetailsRow2(fieldName = "Залишок:", value = "${item.quantityInStock} шт")
                DetailsRow2(fieldName = "Ціна:", value = "${item.price} ${item.currency}")
                DetailsRow2(fieldName = "На сайті:", value = if (item.available) "ТАК" else "НІ")
//                ProductAvailability2(available = item.available)
            }
            HorizontalDivider(Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
            DetailsRow2(fieldName = "МІСЦЕ:", value = item.placeList.joinToString("\n"), onClick = onPlace)
            HorizontalDivider(Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
            ProductCount2(count = item.amount, onClick = onAmount)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProductAvailability2(available: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "На сайті:",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(end = 8.dp)
                .width(100.dp)
        )
        Text(
            text = if (available) "In stock" else "Out of stock",
            color = if (available) Color.Green else Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun ProductName(name: String, onClick: (() -> Unit)? = null) {
    Row {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .weight(10f)
        )
        onClick?.let {
            IconButton(onClick = { it.invoke() }, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun DetailsRow2(fieldName: String, value: String, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = fieldName,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.W300,
            modifier = Modifier
                .padding(end = 8.dp)
                .width(100.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.W400,
//            color = MaterialTheme.colorScheme.primary
            modifier = Modifier.weight(10f)
        )
        onClick?.let {
            IconButton(onClick = onClick, modifier = Modifier.align(Alignment.CenterVertically)) {
                Icon(
                    Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@SuppressLint("AutoboxingStateCreation")
@Composable
fun ProductCount2(count: Int, onClick: () -> Unit) {
    val countState = remember { mutableIntStateOf(count) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Кількість:",
//            style = MaterialTheme.typography.titleMedium,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.W300,
            modifier = Modifier
                .padding(end = 8.dp)
                .width(100.dp)
//                .weight(10f)
        )


        Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)) {
            Column(
                modifier = Modifier
                    .clickable { if (countState.intValue > 0) --countState.intValue }
                    .padding(end = 4.dp),
            ) {
                Text(
                    text = "-1",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)) {
            Column(
                modifier = Modifier.clickable { onClick?.invoke() },
            ) {
                Text(
                    text = "${countState.intValue}",// шт
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp)//.weight(10f)
                )
            }
        }
        Spacer(modifier = Modifier.width(4.dp))
        Card(elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)) {
            Column(
                modifier = Modifier.clickable { ++countState.intValue },
            ) {
                Text(
                    text = "+1",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF01B713),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
            }
        }
    }
}


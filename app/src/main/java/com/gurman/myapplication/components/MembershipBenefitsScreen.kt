package com.gurman.myapplication.components


import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.gurman.myapplication.R
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MembershipBenefitsScreen(
    onBackClick: () -> Unit = {}
) {
    val pagerState = rememberPagerState(pageCount = { 6 })

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Membership Benefits") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        containerColor = Color.Black
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Horizontal Pager for membership cards
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentPadding = PaddingValues(horizontal = 48.dp),
                pageSpacing = 16.dp
            ) { page ->
                MembershipCard(
                    type = when (page) {
                        0 -> "Blue"
                        1 -> "Silver"
                        2 -> "Gold"
                        3 -> "Platinum"
                        4 -> "Diamond"
                        5 -> "Black"
                        else -> "Unknown"
                    },
                    location = "Melbourne",
                    color = when (page) {
                        0 -> Color(0xFF1565C0)
                        1 -> Color(0xFF9E9E9E)
                        2 -> Color(0xFFFFD700)
                        3 -> Color(0xFFE5E4E2)
                        4 -> Color(0xFF00BCD4)
                        5 -> Color(0xFF212121)
                        else -> Color.Gray
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer {
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                    ).absoluteValue

                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )

                            scaleY = lerp(
                                start = 0.85f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Pager indicator
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(6) { index ->
                    val isSelected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (isSelected) 8.dp else 6.dp)
                            .background(
                                color = if (isSelected) Color.White else Color.Gray,
                                shape = CircleShape
                            )
                    )
                }
            }

            // Membership details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = when (pagerState.currentPage) {
                        0 -> "Blue (Melbourne)"
                        1 -> "Silver (Melbourne)"
                        2 -> "Gold (Melbourne)"
                        3 -> "Platinum (Melbourne)"
                        4 -> "Diamond (Melbourne)"
                        5 -> "Black (Melbourne)"
                        else -> ""
                    },
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Requires ${(pagerState.currentPage + 1) * 10} status credits",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Benefits list
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    BenefitSection(
                        title = "DINING & ENTERTAINMENT BENEFITS",
                        benefits = listOf(
                            BenefitItem(
                                icon = Icons.Filled.AccountCircle,
                                text = "Discounted movie tickets every day wit..."
                            )
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    BenefitSection(
                        title = "HOTEL & LEISURE BENEFITS",
                        benefits = listOf(
                            BenefitItem(
                                icon = Icons.Default.Home,
                                text = "10% off Crown Hotels"
                            ),
                            BenefitItem(
                                icon = Icons.Default.Home,
                                text = "10% off Crown Spas"
                            ),
                            BenefitItem(
                                icon = Icons.Default.Home,
                                text = "Hotel welcome gift"
                            )
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    BenefitSection(
                        title = "PARKING & TRAVEL BENEFITS",
                        benefits = listOf(
                            BenefitItem(
                                icon = Icons.Default.Home,
                                text = "Free parking - Multi Level"
                            )
                        )
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
}

@Composable
fun MembershipCard(
    type: String,
    location: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "CROWN REWARDS",
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Crown icon
                Icon(
                    painter = painterResource(id = R.drawable.icons8_more_64), // You'll need to add this
                    contentDescription = "Crown",
                    tint = Color.White,
                    modifier = Modifier.size(80.dp)
                )
            }
        }
    }
}

@Composable
fun BenefitSection(
    title: String,
    benefits: List<BenefitItem>
) {
    Column {
        Text(
            text = title,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            letterSpacing = 0.5.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        benefits.forEach { benefit ->
            BenefitRow(benefit)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun BenefitRow(benefit: BenefitItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(Color(0xFF2C2C2C), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = benefit.icon,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = benefit.text,
            fontSize = 16.sp,
            color = Color.White
        )
    }
}

data class BenefitItem(
    val icon: ImageVector,
    val text: String
)
package com.gurman.myapplication.feature.pager

//import androidx.compose.animation.core.FastOutSlowInEasing
//import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.gurman.myapplication.R
import kotlin.math.absoluteValue

val tiersListMock = arrayListOf(
    R.drawable.tier_card_member,
    R.drawable.tier_card_silver,
    R.drawable.tier_card_gold,
    R.drawable.tier_card_platinum,
    R.drawable.tier_card_black
)

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun MembershipCardsPagerComponent(
    pagerState: PagerState = rememberPagerState(pageCount = { tiersListMock.size }),
    tiers: List<Int> = tiersListMock,
) {
    val gradientColors = listOf(Color(0xE6000000), Color(0x80AAAAAA))

    val flingBehavior = PagerDefaults.flingBehavior(
        state = pagerState,
        pagerSnapDistance = PagerSnapDistance.atMost(5),
        snapAnimationSpec = tween(
            durationMillis = 600, // Збільшуй для повільнішої прокрутки
            easing = FastOutSlowInEasing
        )
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .background(
                brush = Brush.verticalGradient(colors = gradientColors)
            ),
        contentPadding = PaddingValues(horizontal = 38.dp, vertical = 26.dp),
        beyondViewportPageCount = 1,
        flingBehavior = flingBehavior
    ) { page ->
        val resId = tiers[page]

        val pageOffset = (
                (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                ).absoluteValue

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Color.Transparent),
            modifier = Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    alpha = lerp(
                        start = 0.4f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    ).coerceIn(0f, 1f)

                    scaleY = lerp(
                        start = 0.8f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )

                    scaleX = lerp(
                        start = 0.85f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )

                    shadowElevation = lerp(
                        start = 0f,
                        stop = 8f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
                .testTag("card")
        ) {
            Image(
                painter = painterResource(resId),
                contentDescription = "image_tier",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("tier_card")
            )
        }
    }
}
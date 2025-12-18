package com.gurman.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.DeviceFontFamilyName
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)


object TestAppTextStyles {
    val fontSansSerifBlack = FontFamily(Font(DeviceFontFamilyName("sans-serif-black")))
    val fontSansSerifMedium = FontFamily(Font(DeviceFontFamilyName("sans-serif-medium")))
    val fontSans900 = FontFamily(Font(DeviceFontFamilyName("crown_sans_900")))

    val SignUpBodyTextStyle = TextStyle(
        fontSize = 16.sp,
        fontFamily = FontFamily.SansSerif, // sans-serif
        letterSpacing = 0.01f.sp,
        textAlign = TextAlign.Center,
        lineHeight = 24.sp
    )

    val SignUpLandingHeaderTextStyle = TextStyle(
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontSansSerifBlack, // sans-serif-black
        letterSpacing = 0.05f.sp,
        textAlign = TextAlign.Center,
        lineHeight = 35.sp
    )
    val ButtonBaseTextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = fontSansSerifMedium, // sans-serif-medium
        letterSpacing = 0.05f.sp,
        textAlign = TextAlign.Center,
        lineHeight = 16.sp
    )

    val SignUpCrownSans = TextStyle(
        fontSize = 38.sp,
        fontFamily = fontSans900,
        letterSpacing = 0.01f.sp,
        textAlign = TextAlign.Center,
        lineHeight = 42.sp
    )

    val SignUpHeadLineTextStyle = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = 0.01f.sp,
        lineHeight = 24.sp
    )

    val SignUpCaptionTextStyle = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 16.sp
    )

    val TitleSmallBold = TextStyle(
        fontSize = 22.sp,
//        fontWeight = FontWeight.Bold,
        fontWeight = FontWeight.W700,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 28.sp,
        letterSpacing = TextUnit(value = 0.35f, TextUnitType.Unspecified)
    )
    val HeadlineRegular = TextStyle(
        fontSize = 17.sp,
//        fontWeight = FontWeight.Bold,
        fontWeight = FontWeight(590),
        fontFamily = FontFamily.SansSerif,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(value = -0.4f, TextUnitType.Unspecified)
    )
    val HeadlineSemiBold = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W600,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = TextUnit(value = -0.40f, TextUnitType.Unspecified),
        lineHeight = 18.sp,
    )
    val BodyRegular = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(value = -0.41f, TextUnitType.Unspecified)
    )
    val BodyMedium = TextStyle(
        fontSize = 17.sp,
        fontWeight = FontWeight.W700,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 22.sp,
        letterSpacing = TextUnit(value = -0.41f, TextUnitType.Unspecified),
    )
    val FootnoteRegular = TextStyle(
        fontSize = 13.sp,
        fontWeight = FontWeight.W400,
        fontFamily = FontFamily.SansSerif,
        lineHeight = 18.sp,
        letterSpacing = TextUnit(value = -0.40f, TextUnitType.Unspecified),
    )
    val LabelMediumRegular = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.W400,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = TextUnit(value = 0f, TextUnitType.Unspecified),
        lineHeight = 16.sp,
    )
    val LabelLargeSemiBold = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.W600,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = TextUnit(value = 0f, TextUnitType.Unspecified),
        lineHeight = 16.sp,
    )
    val LabelSmallRegular = TextStyle(
        fontSize = 12.sp,
        fontWeight = FontWeight.W400,
        fontFamily = FontFamily.SansSerif,
        letterSpacing = TextUnit(value = 0.07f, TextUnitType.Unspecified),
        lineHeight = 13.sp,
    )
    val LabelRegular = TextStyle(
        fontSize = 13.sp,
//        fontWeight = FontWeight(500),
        fontFamily = FontFamily.SansSerif,
        letterSpacing = TextUnit(value = -0.40f, TextUnitType.Unspecified),
        lineHeight = 16.sp,
    )
    val BodyEmphasized = TextStyle(
        fontSize = 17.sp,
        fontWeight = FontWeight(590),
        fontFamily = FontFamily.SansSerif,
        letterSpacing = TextUnit(value = -0.40f, TextUnitType.Unspecified),
        lineHeight = 22.sp,
    )

}

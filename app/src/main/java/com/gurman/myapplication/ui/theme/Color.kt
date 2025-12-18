package com.gurman.myapplication.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

val white = Color(0xFFFFFFFF)
val black = Color(0xFF000000)
val maud_black = Color(0xFF0E0909)

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)


@Immutable
data class TestAppColorScheme(
    val surfacePrimaryColor: Color,
    // Cta
    val textCtaPrimaryColor: Color,
    val textCtaPrimaryDisabledColor: Color,
    val surfaceCtaPrimaryColor: Color,
    val surfaceCtaPrimaryDisabledColor: Color,

    val textCtaSecondaryColor: Color,
    val textCtaSecondaryDisabledColor: Color,
//    val surfaceCtaSecondaryColor: Color,
//    val surfaceCtaSecondaryDisabledColor: Color,

    val surfaceSurfacePrimaryElement: Color,
    val backgroundGrey: Color,
    val semanticBlue: Color,
    val surfaceCtaDisabled: Color,
    val textCtaPrimaryDisabled: Color,
    val backgroundBlur: Color,
    val dialogBackground: Color,
    val dialogDivider: Color,
    val borders: Color,
    val bordersPrimary: Color,
    val surfacePrimaryElement: Color,
    val surfacePrimaryBackground: Color,
    val surfaceDisabled: Color,
    val semanticGreen: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textDisableDark: Color,
    val textCtaPrimary: Color,
    val textDisabled: Color,
    val textCtaTertiary: Color,
)

fun lightCrownTheme() = TestAppColorScheme(
    surfacePrimaryColor = Color(0xFFFFFFFF),

    textCtaPrimaryColor = Color(0xFFFFFFFF),
    textCtaPrimaryDisabledColor = Color(0xFFFFFFFF),
    surfaceCtaPrimaryColor = Color(0xFF0E0909),
    surfaceCtaPrimaryDisabledColor = Color(0xFF4D4845),
    textCtaSecondaryColor = Color(0xFF0E0909),
    textCtaSecondaryDisabledColor = Color(0xFF4D4845),
//    surfaceCtaSecondaryColor = Color(0x00000000),
//    surfaceCtaSecondaryDisabledColor = Color(0x00000000),
    surfaceSurfacePrimaryElement = Color(0xFF222222),
    backgroundGrey = Color(0xffF8F8F8),
    semanticBlue = Color(0xFF057CB1),
    surfaceCtaDisabled = Color(0xFF4D4845),
    textCtaPrimaryDisabled = Color(0xFFFFFFFF),
    backgroundBlur = Color(0xA8FCF8EA),
    dialogBackground = Color(0xFFDFDFDF),
    dialogDivider = Color(0xA6BAB5B1),
    borders = Color(0xFFBAB5B1),
    bordersPrimary = Color(0xFFBAB5B1),
    surfacePrimaryElement = Color(0xFFF8F8F8),
    surfacePrimaryBackground = Color(0xFFFFFFFF),
    surfaceDisabled = Color(0xFFBAB5B1),
    semanticGreen = Color(0xFF027200),
    textPrimary = Color(0xFF0E0909),
    textSecondary = Color(0xFF222222),
    textDisableDark = Color(0xFF4D4845),
    textCtaPrimary = Color(0xFFFFFFFF),
    textDisabled = Color(0xFFBAB5B1),
    textCtaTertiary = Color(0xFF0E0909),
)

fun darkThemeDark() = TestAppColorScheme(
    surfacePrimaryColor = Color(0xFF0E0909),

    textCtaPrimaryColor = Color(0xFF222222),
    textCtaPrimaryDisabledColor = Color(0xFFFFFFFF),
    surfaceCtaPrimaryColor = Color(0xFFF8F4E9),
    surfaceCtaPrimaryDisabledColor = Color(0xFFBAB5B1),

    textCtaSecondaryColor = Color(0xFFF8F4E9),
    textCtaSecondaryDisabledColor = Color(0xFFBAB5B1),
//    surfaceCtaSecondaryColor = Color(0x00000000),
//    surfaceCtaSecondaryDisabledColor = Color(0x00000000),
    surfaceSurfacePrimaryElement = Color(0xFFFFFFFF),
    backgroundGrey = Color(0xff0E0909),
    semanticBlue = Color(0xFF009ADB),
    surfaceCtaDisabled = Color(0xFFBAB5B1),
    textCtaPrimaryDisabled = Color(0xFF0E0909),
    backgroundBlur = Color(0xAB222222),
    dialogBackground = Color(0xFF222222),
    dialogDivider = Color(0xA6545458),
    borders = Color(0xFF4C4C4C),
    bordersPrimary = Color(0xFF4D4845),
    surfacePrimaryElement = Color(0xFF222222),
    surfacePrimaryBackground = Color(0xFF0E0909),
    surfaceDisabled = Color(0xFF4D4845),
    semanticGreen = Color(0xFF03B800),
    textPrimary = Color(0xFFFFFFFF),
    textSecondary = Color(0xFFEBEBEB),
    textDisableDark = Color(0xFFBAB5B1),
    textCtaPrimary = Color(0xFF0E0909),
    textDisabled = Color(0xFF4D4845),
    textCtaTertiary = Color(0xFFFCF8EA),

    )

//@Composable
//fun myTextFieldColors() = TextFieldDefaults.colors(
//    focusedTextColor = colorResource(id = R.color.text_color),
//    unfocusedTextColor = colorResource(id = R.color.text_color),
//    disabledTextColor = colorResource(id = R.color.text_color_disable),
//
//    focusedContainerColor = colorResource(id = R.color.background_charcoal),
//    unfocusedContainerColor = colorResource(id = R.color.signup_background_row_holder),
//
//    errorIndicatorColor = colorResource(id = R.color.error_color),
//    errorCursorColor = colorResource(id = R.color.error_color),
//    errorSupportingTextColor = colorResource(id = R.color.error_color),
//
//    cursorColor = colorResource(id = R.color.signup_icon_color),
//    errorTrailingIconColor = colorResource(id = R.color.signup_icon_color),
//    focusedTrailingIconColor = colorResource(id = R.color.signup_icon_color),
//    unfocusedTrailingIconColor = colorResource(id = R.color.signup_icon_color),
//
//    focusedIndicatorColor = colorResource(id = R.color.maud_divider),
//    unfocusedIndicatorColor = colorResource(id = R.color.maud_divider),
//    disabledIndicatorColor = colorResource(id = R.color.text_color_disable),
//)
//
//@Composable
//fun limitTextFieldColors() = TextFieldDefaults.colors(
//    focusedTextColor = colorResource(id = R.color.text_color),
//    unfocusedTextColor = colorResource(id = R.color.text_color),
//    disabledTextColor = colorResource(id = R.color.text_color_disable),
//
//    focusedContainerColor = colorResource(id = R.color.background_charcoal),
//    unfocusedContainerColor = colorResource(id = R.color.signup_background_row_holder),
//
//    errorIndicatorColor = colorResource(id = R.color.text_color_disable),
////    errorCursorColor = colorResource(id = R.color.error_color),
////    errorSupportingTextColor = colorResource(id = R.color.error_color),
//
//    cursorColor = colorResource(id = R.color.signup_icon_color),
//    errorTrailingIconColor = colorResource(id = R.color.signup_icon_color),
//    focusedTrailingIconColor = colorResource(id = R.color.signup_icon_color),
//    unfocusedTrailingIconColor = colorResource(id = R.color.signup_icon_color),
//
//    focusedIndicatorColor = colorResource(id = R.color.maud_divider),
//    unfocusedIndicatorColor = colorResource(id = R.color.maud_divider),
//    disabledIndicatorColor = colorResource(id = R.color.text_color_disable),
//)
//
//@Composable
//fun limitTextFieldColors2(colors: CrownColorScheme) = TextFieldDefaults.colors(
//    focusedTextColor = colorResource(id = R.color.text_color),
//    unfocusedTextColor = colorResource(id = R.color.text_color),
//    disabledTextColor = colorResource(id = R.color.text_color_disable),
//
//    focusedContainerColor = colors.surfacePrimaryBackground, // colorResource(id = R.color.background_charcoal),
//    unfocusedContainerColor = colors.surfacePrimaryBackground, // colorResource(id = R.color.signup_background_row_holder),
//
//    errorIndicatorColor = colorResource(id = R.color.text_color_disable),
////    errorCursorColor = colorResource(id = R.color.error_color),
////    errorSupportingTextColor = colorResource(id = R.color.error_color),
//
//    cursorColor = colorResource(id = R.color.signup_icon_color),
//
//    focusedTrailingIconColor = colorResource(id = R.color.signup_icon_color),
//    errorTrailingIconColor = colorResource(id = R.color.signup_icon_color),
//    unfocusedTrailingIconColor = colorResource(id = R.color.signup_icon_color),
//
//    focusedIndicatorColor = colors.bordersPrimary, // colorResource(id = R.color.maud_divider),
//    unfocusedIndicatorColor = colors.bordersPrimary, //colorResource(id = R.color.maud_divider),
////    disabledIndicatorColor = colors.surfacePrimaryBackground, // colorResource(id = R.color.text_color_disable),
//)

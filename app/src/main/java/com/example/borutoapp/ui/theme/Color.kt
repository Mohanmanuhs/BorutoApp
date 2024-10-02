package com.example.borutoapp.ui.theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val Purple700 = Color(0xFF3700B3)
val Purple500 = Color(0xD58A44EE)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val LightGray = Color(0xFFD8D8D8)
val DarkGray = Color(0xFF2A2A2A)

val StarColor = Color(0xFFFFC94D)

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFE3E3E3)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val statusBarColor
    @Composable
    get() = if (!isSystemInDarkTheme()) Purple700 else Color.Black


val welcomeScreenBackgroundColor
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else Color.Black

val titleColor
    @Composable
    get() = if (!isSystemInDarkTheme()) DarkGray else LightGray

val descriptionColor
    @Composable
    get() = if (!isSystemInDarkTheme()) DarkGray.copy(alpha = 0.7f)
    else LightGray.copy(alpha = 0.5f)

val activeIndicatorColor
    @Composable
    get() = if (!isSystemInDarkTheme()) Purple500 else Purple700

val inactiveIndicatorColor
    @Composable
    get() = if (!isSystemInDarkTheme()) LightGray else DarkGray

val buttonBackgroundColor
    @Composable
    get() = if (!isSystemInDarkTheme()) Purple500 else Purple700

val topAppBarContentColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else LightGray

val topAppBarBackgroundColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Purple500 else Color.Black
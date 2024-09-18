package com.arshia.moviedatademo.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Thunder = Color(0xFF140F14)
val BlackMarlin = Color(0xFF312617)
val Kumera = Color(0xFF876C1F)
val Nugget = Color(0xFFC09B24)
val LightningYellow = Color(0xFFF9C929)

val DeepMagenta = Color(0XFF9F025E)
val MulberryWood = Color(0xFF5A0939)


val BackgroundBrush = Brush.verticalGradient(
    0f to LightningYellow,
    0.2f to Kumera,
    1f to Thunder,
    startY = 0.0f,
)

val failureBackgroundBrush = Brush.verticalGradient(
    0f to DeepMagenta,
    1f to Thunder,
    startY = 0.0f,
)

val SearchContentBrush = Brush.verticalGradient(
    0f to DeepMagenta,
    1f to MulberryWood,
    startY = 0.0f,
)

val DetailsBackgroundBrush = Brush.verticalGradient(
    0f to LightningYellow,
    1f to DeepMagenta,
    startY = 0.0f,
)
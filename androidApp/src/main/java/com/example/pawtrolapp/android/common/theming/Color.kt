package com.example.pawtrolapp.android.common.theming

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

val Blue = Color(0xFF2A9D8F)
val Yellow = Color(0xFFE9C46A)
val Gray = Color(0xFFF3F3F4)


val Black = Color(0xFF000000)

val Black87 = Color(0xFF18191A)
val DarkGray = Color(0xFF999A9A)

val Black54 = Color(0xFF373B3F)
val Black24 = Color(0xFF242526)


val White = Color(0xFFFFFFFF)

val White87 = Color(0xFFE2E2E2)
val LightGray = Color(0xFF8A8A8D)

val White36 = Color(0xFFE5E5E5)
val White76 = Color(0xFFF5F0EC)


internal val LightColors = lightColorScheme(
    primary = Blue,
    secondary = Yellow,
    background = White76,
    onBackground = Black87,
    surface = White,
    onSurface = Black87
)

internal val DarkColors = darkColorScheme(
    primary = Blue,
    secondary = Yellow,
    background = Black87,
    onBackground = White87,
    surface = Black24,
    onSurface = White87
)

@Composable
fun ColorScheme.isLight() = this.background.luminance() > 0.5
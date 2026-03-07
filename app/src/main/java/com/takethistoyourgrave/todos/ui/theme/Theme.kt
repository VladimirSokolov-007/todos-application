package com.takethistoyourgrave.todos.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val IOSColorScheme = lightColorScheme(
    primary = IOSBlue,
    onPrimary = Color.White,
    background = IOSBackground,
    surface = IOSCardBackground,
    onBackground = Color.Black,
    onSurface = Color.Black,
    error = IOSRed,
    outline = IOSGray5
)

@Composable
fun ToDosTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = IOSColorScheme,
        typography = Typography,
        content = content
    )
}

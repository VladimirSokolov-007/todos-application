package com.takethistoyourgrave.todos.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takethistoyourgrave.todos.ui.theme.IOSBackground
import com.takethistoyourgrave.todos.ui.theme.IOSBlue
import com.takethistoyourgrave.todos.ui.theme.IOSGray

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerScreen(
    initialColor: Int,
    onColorSelected: (Int) -> Unit,
    onBack: () -> Unit
) {
    val hsv = remember {
        val arr = FloatArray(3)
        android.graphics.Color.colorToHSV(initialColor, arr)
        arr
    }

    var hue by remember { mutableFloatStateOf(hsv[0]) }
    var saturation by remember { mutableFloatStateOf(hsv[1]) }
    var brightness by remember { mutableFloatStateOf(hsv[2]) }

    val currentColor = Color(
        android.graphics.Color.HSVToColor(floatArrayOf(hue, saturation, brightness))
    )

    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IOSBackground)
    ) {
        TopAppBar(
            title = { Text("Выбор цвета", fontWeight = FontWeight.SemiBold) },
            navigationIcon = {
                TextButton(onClick = onBack) {
                    Text("Назад", color = IOSBlue)
                }
            },
            actions = {
                TextButton(onClick = {
                    onColorSelected(
                        android.graphics.Color.HSVToColor(floatArrayOf(hue, saturation, brightness))
                    )
                    onBack()
                }) {
                    Text("Готово", color = IOSBlue, fontWeight = FontWeight.SemiBold)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = IOSBackground)
        )

        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(
                initialOffsetY = { it / 3 },
                animationSpec = tween(400)
            ) + fadeIn(animationSpec = tween(400))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = scaleIn(
                        initialScale = 0.3f,
                        animationSpec = tween(500)
                    ) + fadeIn(animationSpec = tween(500))
                ) {
                    Box(
                        modifier = Modifier
                            .size(70.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(currentColor)
                            .align(Alignment.CenterHorizontally)
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(16.dp)
                ) {
                    Text("Яркость", fontSize = 13.sp, color = IOSGray)
                    Slider(
                        value = brightness,
                        onValueChange = { brightness = it },
                        valueRange = 0f..1f,
                        colors = SliderDefaults.colors(
                            thumbColor = IOSBlue,
                            activeTrackColor = IOSBlue
                        )
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Выберите цвет", fontSize = 13.sp, color = IOSGray)
                    ColorGradient(
                        brightness = brightness,
                        selectedHue = hue,
                        selectedSaturation = saturation,
                        onColorPicked = { h, s ->
                            hue = h
                            saturation = s
                        }
                    )
                }
            }
        }
    }
}

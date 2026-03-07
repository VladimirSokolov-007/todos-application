package com.takethistoyourgrave.todos.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.dp

@Composable
fun ColorGradient(
    brightness: Float,
    selectedHue: Float,
    selectedSaturation: Float,
    onColorPicked: (hue: Float, saturation: Float) -> Unit
) {
    var widthPx by remember { mutableFloatStateOf(1f) }
    var heightPx by remember { mutableFloatStateOf(1f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(RoundedCornerShape(12.dp))
            .onSizeChanged {
                widthPx = it.width.toFloat()
                heightPx = it.height.toFloat()
            }
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val h = (offset.x / widthPx * 360f).coerceIn(0f, 360f)
                    val s = (1f - offset.y / heightPx).coerceIn(0f, 1f)
                    onColorPicked(h, s)
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    change.consume()
                    val h = (change.position.x / widthPx * 360f).coerceIn(0f, 360f)
                    val s = (1f - change.position.y / heightPx).coerceIn(0f, 1f)
                    onColorPicked(h, s)
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            for (x in 0 until size.width.toInt()) {
                val hue = x / size.width * 360f
                val topColor = Color(
                    android.graphics.Color.HSVToColor(floatArrayOf(hue, 1f, brightness))
                )
                val bottomColor = Color(
                    android.graphics.Color.HSVToColor(floatArrayOf(hue, 0f, brightness))
                )
                drawLine(
                    brush = Brush.verticalGradient(listOf(topColor, bottomColor)),
                    start = Offset(x.toFloat(), 0f),
                    end = Offset(x.toFloat(), size.height),
                    strokeWidth = 1f
                )
            }

            val crossX = selectedHue / 360f * size.width
            val crossY = (1f - selectedSaturation) * size.height
            val crossSize = 14f

            drawCircle(
                color = Color(
                    android.graphics.Color.HSVToColor(
                        floatArrayOf(selectedHue, selectedSaturation, brightness)
                    )
                ),
                radius = crossSize,
                center = Offset(crossX, crossY)
            )
            drawCircle(
                color = Color.White,
                radius = crossSize,
                center = Offset(crossX, crossY),
                style = Stroke(width = 3f)
            )
        }
    }
}

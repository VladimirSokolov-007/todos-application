package com.takethistoyourgrave.todos.ui.components

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.takethistoyourgrave.todos.ui.theme.IOSBlue

@Composable
fun ColorSquare(color: Int, isSelected: Boolean, onClick: () -> Unit) {
    val shape = RoundedCornerShape(8.dp)
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(shape)
            .background(Color(color))
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) IOSBlue else Color(0xFFD1D1D6),
                shape = shape
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (color == AndroidColor.WHITE || color == AndroidColor.YELLOW || color == AndroidColor.CYAN) {
                    Color.Black
                } else {
                    Color.White
                },
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

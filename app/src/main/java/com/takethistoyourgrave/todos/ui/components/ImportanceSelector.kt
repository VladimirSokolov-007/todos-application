package com.takethistoyourgrave.todos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takethistoyourgrave.todos.model.Importance
import com.takethistoyourgrave.todos.ui.theme.IOSGray5

private val importanceColors = mapOf(
    Importance.LOW to Color(0xFF8E8E93),
    Importance.NORMAL to Color(0xFF007AFF),
    Importance.HIGH to Color(0xFFFF3B30)
)

@Composable
fun ImportanceSelector(selected: Importance, onSelect: (Importance) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(IOSGray5)
            .padding(3.dp)
    ) {
        Importance.entries.forEach { imp ->
            val isSelected = selected == imp
            val color = importanceColors[imp] ?: Color.Black

            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) Color.White else Color.Transparent)
                    .clickable { onSelect(imp) }
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = imp.label,
                    fontSize = 14.sp,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isSelected) color else Color(0xFF636366),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

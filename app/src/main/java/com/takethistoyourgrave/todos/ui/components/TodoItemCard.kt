package com.takethistoyourgrave.todos.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takethistoyourgrave.todos.domain.model.Importance
import com.takethistoyourgrave.todos.domain.model.TodoItem
import com.takethistoyourgrave.todos.ui.theme.IOSGray
import com.takethistoyourgrave.todos.ui.theme.IOSGreen
import com.takethistoyourgrave.todos.ui.theme.IOSRed
import java.time.format.DateTimeFormatter

@Composable
fun TodoItemCard(item: TodoItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .alpha(if (item.isDone) 0.4f else 1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color(item.color), CircleShape)
                    .border(1.dp, Color(0xFFD1D1D6), CircleShape)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = item.text,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal
                )

                if (item.deadline != null) {
                    Text(
                        text = item.deadline.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        fontSize = 13.sp,
                        color = IOSGray
                    )
                }

                if (item.importance != Importance.NORMAL) {
                    Text(
                        text = item.importance.label,
                        fontSize = 13.sp,
                        color = if (item.importance == Importance.HIGH) IOSRed else IOSGray
                    )
                }
            }

            Switch(
                checked = item.isDone,
                onCheckedChange = null,
                colors = SwitchDefaults.colors(
                    checkedTrackColor = IOSGreen,
                    checkedThumbColor = Color.White
                )
            )
        }
    }
}

package com.takethistoyourgrave.todos.ui.components

import android.graphics.Color as AndroidColor
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takethistoyourgrave.todos.domain.model.Importance
import com.takethistoyourgrave.todos.ui.theme.IOSBackground
import com.takethistoyourgrave.todos.ui.theme.IOSBlue
import com.takethistoyourgrave.todos.ui.theme.IOSGray
import com.takethistoyourgrave.todos.ui.theme.IOSGreen
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.core.graphics.toColorInt

private val presetColors = listOf(
    AndroidColor.WHITE,
    AndroidColor.RED,
    AndroidColor.GREEN,
    AndroidColor.BLUE,
    AndroidColor.YELLOW,
    AndroidColor.CYAN,
    AndroidColor.MAGENTA,
    "#FF9800".toColorInt(),
)

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun TodoFormContent(
    title: String,
    text: String,
    importance: Importance,
    isDone: Boolean,
    color: Int,
    customColor: Int?,
    deadline: LocalDateTime?,
    showDatePicker: Boolean,
    onTextChange: (String) -> Unit,
    onImportanceChange: (Importance) -> Unit,
    onIsDoneChange: (Boolean) -> Unit,
    onColorChange: (Int) -> Unit,
    onDeadlineChange: (LocalDateTime?) -> Unit,
    onShowDatePicker: () -> Unit,
    onHideDatePicker: () -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onOpenColorPicker: () -> Unit
) {
    val contentAlpha = if (isDone) 0.4f else 1f

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(IOSBackground)
    ) {
        TopAppBar(
            title = { Text(title, fontWeight = FontWeight.SemiBold) },
            navigationIcon = {
                TextButton(onClick = onBack) {
                    Text("Отмена", color = IOSBlue)
                }
            },
            actions = {
                TextButton(onClick = onSave) {
                    Text("Сохранить", color = IOSBlue, fontWeight = FontWeight.SemiBold)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = IOSBackground)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .alpha(contentAlpha),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = text,
                onValueChange = onTextChange,
                placeholder = { Text("Что нужно сделать?", color = IOSGray) },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 120.dp)
                    .clip(RoundedCornerShape(12.dp)),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                maxLines = 10
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Важность", fontSize = 13.sp, color = IOSGray)
                ImportanceSelector(selected = importance, onSelect = onImportanceChange)

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Выполнено", fontSize = 17.sp)
                    Switch(
                        checked = isDone,
                        onCheckedChange = onIsDoneChange,
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = IOSGreen,
                            checkedThumbColor = Color.White
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onShowDatePicker() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Сделать до", fontSize = 17.sp)
                        Text(
                            text = deadline?.format(DateTimeFormatter.ofPattern("dd MMMM yyyy"))
                                ?: "Не выбрано",
                            fontSize = 13.sp,
                            color = if (deadline != null) IOSBlue else IOSGray
                        )
                    }
                    if (deadline != null) {
                        TextButton(onClick = { onDeadlineChange(null) }) {
                            Text("Убрать", color = IOSBlue)
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text("Цвет заметки", fontSize = 13.sp, color = IOSGray)

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    presetColors.forEach { c ->
                        ColorSquare(
                            color = c,
                            isSelected = color == c,
                            onClick = { onColorChange(c) }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    listOf(
                                        Color.Red, Color.Yellow, Color.Green,
                                        Color.Cyan, Color.Blue, Color.Magenta
                                    )
                                )
                            )
                            .border(
                                width = if (customColor != null && color == customColor) 2.dp else 1.dp,
                                color = if (customColor != null && color == customColor) IOSBlue else Color(0xFFD1D1D6),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .combinedClickable(
                                onClick = { onOpenColorPicker() },
                                onLongClick = { onOpenColorPicker() }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        if (customColor != null && color == customColor) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    if (showDatePicker) {
        DeadlineDatePicker(
            initialDate = deadline,
            onDateSelected = { onDeadlineChange(it) },
            onDismiss = { onHideDatePicker() }
        )
    }
}

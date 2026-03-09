package com.takethistoyourgrave.todos.ui.edit

import android.graphics.Color
import com.takethistoyourgrave.todos.domain.model.Importance
import java.time.LocalDateTime

data class EditTodoState(
    val uid: String = "",
    val text: String = "",
    val importance: Importance = Importance.NORMAL,
    val isDone: Boolean = false,
    val color: Int = Color.WHITE,
    val customColor: Int? = null,
    val deadline: LocalDateTime? = null,
    val showDatePicker: Boolean = false
)

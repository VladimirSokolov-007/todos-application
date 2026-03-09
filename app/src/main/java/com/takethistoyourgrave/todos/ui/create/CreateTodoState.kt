package com.takethistoyourgrave.todos.ui.create

import android.graphics.Color
import com.takethistoyourgrave.todos.domain.model.Importance
import java.time.LocalDateTime

data class CreateTodoState(
    val text: String = "",
    val importance: Importance = Importance.NORMAL,
    val isDone: Boolean = false,
    val color: Int = Color.WHITE,
    val customColor: Int? = null,
    val deadline: LocalDateTime? = null,
    val showDatePicker: Boolean = false,
    val isSaved: Boolean = false
)

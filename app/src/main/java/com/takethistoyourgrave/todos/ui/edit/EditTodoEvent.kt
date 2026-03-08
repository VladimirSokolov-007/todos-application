package com.takethistoyourgrave.todos.ui.edit

import com.takethistoyourgrave.todos.domain.model.Importance
import java.time.LocalDateTime

sealed class EditTodoEvent {
    data class LoadItem(val uid: String) : EditTodoEvent()
    data class UpdateText(val text: String) : EditTodoEvent()
    data class UpdateImportance(val importance: Importance) : EditTodoEvent()
    data class UpdateIsDone(val isDone: Boolean) : EditTodoEvent()
    data class UpdateColor(val color: Int) : EditTodoEvent()
    data class UpdateCustomColor(val color: Int) : EditTodoEvent()
    data class UpdateDeadline(val deadline: LocalDateTime?) : EditTodoEvent()
    object ShowDatePicker : EditTodoEvent()
    object HideDatePicker : EditTodoEvent()
    object Save : EditTodoEvent()
}

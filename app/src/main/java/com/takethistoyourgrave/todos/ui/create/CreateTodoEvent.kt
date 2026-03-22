package com.takethistoyourgrave.todos.ui.create

import com.takethistoyourgrave.todos.domain.model.Importance
import java.time.LocalDateTime

sealed class CreateTodoEvent {
    data class UpdateText(val text: String) : CreateTodoEvent()
    data class UpdateImportance(val importance: Importance) : CreateTodoEvent()
    data class UpdateIsDone(val isDone: Boolean) : CreateTodoEvent()
    data class UpdateColor(val color: Int) : CreateTodoEvent()
    data class UpdateCustomColor(val color: Int) : CreateTodoEvent()
    data class UpdateDeadline(val deadline: LocalDateTime?) : CreateTodoEvent()
    object ShowDatePicker : CreateTodoEvent()
    object HideDatePicker : CreateTodoEvent()
    object Save : CreateTodoEvent()
}

sealed class CreateTodoAction {
    data object NavigateBack : CreateTodoAction()
}
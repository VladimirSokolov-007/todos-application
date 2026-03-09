package com.takethistoyourgrave.todos.ui.create

import androidx.lifecycle.ViewModel
import com.takethistoyourgrave.todos.domain.TodoRepository
import com.takethistoyourgrave.todos.domain.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CreateTodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateTodoState())
    val state: StateFlow<CreateTodoState> = _state

    fun onEvent(event: CreateTodoEvent) {
        when (event) {
            is CreateTodoEvent.UpdateText -> {
                _state.value = _state.value.copy(text = event.text)
            }

            is CreateTodoEvent.UpdateImportance -> {
                _state.value = _state.value.copy(importance = event.importance)
            }

            is CreateTodoEvent.UpdateIsDone -> {
                _state.value = _state.value.copy(isDone = event.isDone)
            }

            is CreateTodoEvent.UpdateColor -> {
                _state.value = _state.value.copy(color = event.color)
            }

            is CreateTodoEvent.UpdateCustomColor -> {
                _state.value = _state.value.copy(
                    customColor = event.color,
                    color = event.color
                )
            }

            is CreateTodoEvent.UpdateDeadline -> {
                _state.value = _state.value.copy(deadline = event.deadline)
            }

            is CreateTodoEvent.ShowDatePicker -> {
                _state.value = _state.value.copy(showDatePicker = true)
            }

            is CreateTodoEvent.HideDatePicker -> {
                _state.value = _state.value.copy(showDatePicker = false)
            }

            is CreateTodoEvent.Save -> {
                val s = _state.value
                if (s.text.isBlank()) return

                val item = TodoItem(
                    text = s.text,
                    importance = s.importance,
                    isDone = s.isDone,
                    color = s.color,
                    deadline = s.deadline
                )
                repository.addItem(item)
                _state.value = s.copy(isSaved = true)
            }
        }
    }
}

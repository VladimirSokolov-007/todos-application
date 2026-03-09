package com.takethistoyourgrave.todos.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takethistoyourgrave.todos.domain.TodoRepository
import com.takethistoyourgrave.todos.domain.model.TodoItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditTodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(EditTodoState())
    val state: StateFlow<EditTodoState> = _state

    fun onEvent(event: EditTodoEvent) {
        when (event) {
            is EditTodoEvent.LoadItem -> {
                val item = repository.getItem(event.uid) ?: return
                _state.value = EditTodoState(
                    uid = item.uid,
                    text = item.text,
                    importance = item.importance,
                    isDone = item.isDone,
                    color = item.color,
                    deadline = item.deadline
                )
            }

            is EditTodoEvent.UpdateText -> {
                _state.value = _state.value.copy(text = event.text)
            }

            is EditTodoEvent.UpdateImportance -> {
                _state.value = _state.value.copy(importance = event.importance)
            }

            is EditTodoEvent.UpdateIsDone -> {
                _state.value = _state.value.copy(isDone = event.isDone)
            }

            is EditTodoEvent.UpdateColor -> {
                _state.value = _state.value.copy(color = event.color)
            }

            is EditTodoEvent.UpdateCustomColor -> {
                _state.value = _state.value.copy(
                    customColor = event.color,
                    color = event.color
                )
            }

            is EditTodoEvent.UpdateDeadline -> {
                _state.value = _state.value.copy(deadline = event.deadline)
            }

            is EditTodoEvent.ShowDatePicker -> {
                _state.value = _state.value.copy(showDatePicker = true)
            }

            is EditTodoEvent.HideDatePicker -> {
                _state.value = _state.value.copy(showDatePicker = false)
            }

            is EditTodoEvent.Save -> {
                val s = _state.value
                if (s.text.isBlank()) return

                val item = TodoItem(
                    uid = s.uid,
                    text = s.text,
                    importance = s.importance,
                    isDone = s.isDone,
                    color = s.color,
                    deadline = s.deadline
                )
                viewModelScope.launch {
                    repository.updateItem(item)
                    _state.value = s.copy(isSaved = true)
                }
            }
        }
    }
}

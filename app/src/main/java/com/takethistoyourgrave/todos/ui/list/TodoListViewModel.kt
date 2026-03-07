package com.takethistoyourgrave.todos.ui.list

import androidx.lifecycle.ViewModel
import com.takethistoyourgrave.todos.data.FileStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TodoListViewModel(private val storage: FileStorage) : ViewModel() {

    private val _state = MutableStateFlow(TodoListState())
    val state: StateFlow<TodoListState> = _state

    init {
        onEvent(TodoListEvent.Load)
    }

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.Load -> {
                _state.value = TodoListState(items = storage.getItems())
            }

            is TodoListEvent.Delete -> {
                storage.remove(event.uid)
                storage.save()
                _state.value = TodoListState(items = storage.getItems())
            }
        }
    }
}

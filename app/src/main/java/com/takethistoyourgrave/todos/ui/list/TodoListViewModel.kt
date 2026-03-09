package com.takethistoyourgrave.todos.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.takethistoyourgrave.todos.domain.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    val state = repository.itemsFlow
        .map { items -> TodoListState(items = items) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, TodoListState())

    init {
        viewModelScope.launch { repository.refresh() }
    }

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.Load -> {
                viewModelScope.launch { repository.refresh() }
            }

            is TodoListEvent.Delete -> {
                viewModelScope.launch { repository.deleteItem(event.uid) }
            }
        }
    }
}

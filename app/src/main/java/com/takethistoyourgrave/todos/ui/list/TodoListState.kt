package com.takethistoyourgrave.todos.ui.list

import com.takethistoyourgrave.todos.domain.model.TodoItem

data class TodoListState(
    val items: List<TodoItem> = emptyList()
)

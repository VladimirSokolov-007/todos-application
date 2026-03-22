package com.takethistoyourgrave.todos.data.remote.dto

data class TodoListResponse(
    val status: String,
    val list: List<TodoItemDto>,
    val revision: Int
)

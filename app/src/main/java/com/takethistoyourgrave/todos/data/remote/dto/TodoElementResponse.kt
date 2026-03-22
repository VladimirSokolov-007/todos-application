package com.takethistoyourgrave.todos.data.remote.dto

data class TodoElementResponse(
    val status: String,
    val element: TodoItemDto,
    val revision: Int
)

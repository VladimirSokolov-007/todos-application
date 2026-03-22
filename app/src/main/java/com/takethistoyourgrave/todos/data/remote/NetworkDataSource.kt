package com.takethistoyourgrave.todos.data.remote

import com.takethistoyourgrave.todos.domain.model.TodoItem

interface NetworkDataSource {
    var revision: Int
    suspend fun loadItems(): List<TodoItem>
    suspend fun addItem(item: TodoItem): TodoItem?
    suspend fun updateItem(item: TodoItem): TodoItem?
    suspend fun deleteItem(uid: String): TodoItem?
    suspend fun patchAll(items: List<TodoItem>): List<TodoItem>
}

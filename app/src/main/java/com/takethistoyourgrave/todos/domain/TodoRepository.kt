package com.takethistoyourgrave.todos.domain

import com.takethistoyourgrave.todos.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    val itemsFlow: Flow<List<TodoItem>>
    fun getItems(): List<TodoItem>
    fun getItem(uid: String): TodoItem?
    suspend fun addItem(item: TodoItem)
    suspend fun updateItem(item: TodoItem)
    suspend fun deleteItem(uid: String)
    suspend fun refresh()
}

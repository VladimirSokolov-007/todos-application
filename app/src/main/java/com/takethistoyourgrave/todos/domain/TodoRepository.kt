package com.takethistoyourgrave.todos.domain

import com.takethistoyourgrave.todos.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    val itemsFlow: Flow<List<TodoItem>>
    fun getItems(): List<TodoItem>
    fun getItem(uid: String): TodoItem?
    fun addItem(item: TodoItem)
    fun updateItem(item: TodoItem)
    fun deleteItem(uid: String)
    fun refresh()
}

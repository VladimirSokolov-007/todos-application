package com.takethistoyourgrave.todos.data.remote

import com.takethistoyourgrave.todos.domain.model.TodoItem

interface NetworkDataSource {
    fun loadItems(): List<TodoItem>
    fun loadItem(uid: String): TodoItem?
    fun sendItem(item: TodoItem)
    fun deleteItem(uid: String)
    fun sendAll(items: List<TodoItem>)
}

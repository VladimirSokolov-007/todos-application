package com.takethistoyourgrave.todos.ui.list

sealed class TodoListEvent {
    object Load : TodoListEvent()
    data class Delete(val uid: String) : TodoListEvent()
}

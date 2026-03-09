package com.takethistoyourgrave.todos.navigation

import kotlinx.serialization.Serializable

@Serializable
data object TodoListKey

@Serializable
data object CreateTodoKey

@Serializable
data class EditTodoKey(val uid: String)

@Serializable
data class ColorPickerKey(val initialColor: Int)

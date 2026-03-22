package com.takethistoyourgrave.todos.navigation

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data object TodoListKey

@Serializable
data class CreateTodoKey(val id: String = UUID.randomUUID().toString())

@Serializable
data class EditTodoKey(val uid: String)

@Serializable
data class ColorPickerKey(val initialColor: Int)

package com.takethistoyourgrave.todos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.takethistoyourgrave.todos.ui.components.ColorPickerScreen
import com.takethistoyourgrave.todos.ui.create.CreateTodoScreen
import com.takethistoyourgrave.todos.ui.edit.EditTodoScreen
import com.takethistoyourgrave.todos.ui.list.TodoListScreen

@Composable
fun ToDosNavigation() {
    val backStack = remember { mutableStateListOf<Any>(TodoListKey) }
    var selectedColor by remember { mutableStateOf<Int?>(null) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is TodoListKey -> NavEntry(key) {
                    TodoListScreen(
                        onItemClick = { item ->
                            backStack.add(EditTodoKey(uid = item.uid))
                        },
                        onAddClick = {
                            backStack.add(CreateTodoKey())
                        }
                    )
                }

                is CreateTodoKey -> NavEntry(key) {
                    CreateTodoScreen(
                        key = key.id,
                        selectedColor = selectedColor,
                        onColorConsumed = { selectedColor = null },
                        onBack = {
                            backStack.removeLastOrNull()
                        },
                        onOpenColorPicker = { color ->
                            backStack.add(ColorPickerKey(initialColor = color))
                        }
                    )
                }

                is EditTodoKey -> NavEntry(key) {
                    EditTodoScreen(
                        uid = key.uid,
                        selectedColor = selectedColor,
                        onColorConsumed = { selectedColor = null },
                        onBack = {
                            backStack.removeLastOrNull()
                        },
                        onOpenColorPicker = { color ->
                            backStack.add(ColorPickerKey(initialColor = color))
                        }
                    )
                }

                is ColorPickerKey -> NavEntry(key) {
                    ColorPickerScreen(
                        initialColor = key.initialColor,
                        onColorSelected = { color ->
                            selectedColor = color
                        },
                        onBack = {
                            backStack.removeLastOrNull()
                        }
                    )
                }

                else -> NavEntry(Unit) {}
            }
        }
    )
}

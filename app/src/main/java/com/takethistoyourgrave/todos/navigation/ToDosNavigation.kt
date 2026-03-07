package com.takethistoyourgrave.todos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.takethistoyourgrave.todos.data.FileStorage
import com.takethistoyourgrave.todos.ui.create.CreateTodoEvent
import com.takethistoyourgrave.todos.ui.create.CreateTodoScreen
import com.takethistoyourgrave.todos.ui.create.CreateTodoViewModel
import com.takethistoyourgrave.todos.ui.edit.EditTodoEvent
import com.takethistoyourgrave.todos.ui.edit.EditTodoScreen
import com.takethistoyourgrave.todos.ui.edit.EditTodoViewModel
import com.takethistoyourgrave.todos.ui.list.TodoListEvent
import com.takethistoyourgrave.todos.ui.list.TodoListScreen
import com.takethistoyourgrave.todos.ui.list.TodoListViewModel
import com.takethistoyourgrave.todos.ui.components.ColorPickerScreen

@Composable
fun ToDosNavigation(storage: FileStorage) {
    val backStack = remember { mutableStateListOf<Any>(TodoListKey) }

    val listViewModel = remember { TodoListViewModel(storage) }
    val createViewModel = remember { CreateTodoViewModel(storage) }
    val editViewModel = remember { EditTodoViewModel(storage) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is TodoListKey -> NavEntry(key) {
                    listViewModel.onEvent(TodoListEvent.Load)
                    TodoListScreen(
                        viewModel = listViewModel,
                        onItemClick = { item ->
                            backStack.add(EditTodoKey(uid = item.uid))
                        },
                        onAddClick = {
                            backStack.add(CreateTodoKey)
                        }
                    )
                }

                is CreateTodoKey -> NavEntry(key) {
                    CreateTodoScreen(
                        viewModel = createViewModel,
                        onBack = {
                            backStack.removeLastOrNull()
                        },
                        onOpenColorPicker = {
                            val color = createViewModel.state.value.customColor
                                ?: createViewModel.state.value.color
                            backStack.add(ColorPickerKey(initialColor = color, fromEdit = false))
                        }
                    )
                }

                is EditTodoKey -> NavEntry(key) {
                    val item = storage.getItems().find { it.uid == key.uid }
                    if (item != null) {
                        EditTodoScreen(
                            viewModel = editViewModel,
                            item = item,
                            onBack = {
                                backStack.removeLastOrNull()
                            },
                            onOpenColorPicker = {
                                val color = editViewModel.state.value.customColor
                                    ?: editViewModel.state.value.color
                                backStack.add(ColorPickerKey(initialColor = color, fromEdit = true))
                            }
                        )
                    }
                }

                is ColorPickerKey -> NavEntry(key) {
                    ColorPickerScreen(
                        initialColor = key.initialColor,
                        onColorSelected = { color ->
                            if (key.fromEdit) {
                                editViewModel.onEvent(EditTodoEvent.UpdateCustomColor(color))
                            } else {
                                createViewModel.onEvent(CreateTodoEvent.UpdateCustomColor(color))
                            }
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

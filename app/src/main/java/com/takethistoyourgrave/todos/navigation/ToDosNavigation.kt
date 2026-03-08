package com.takethistoyourgrave.todos.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import com.takethistoyourgrave.todos.ui.create.CreateTodoScreen
import com.takethistoyourgrave.todos.ui.edit.EditTodoScreen
import com.takethistoyourgrave.todos.ui.list.TodoListScreen
import com.takethistoyourgrave.todos.ui.components.ColorPickerScreen
import com.takethistoyourgrave.todos.ui.create.CreateTodoEvent
import com.takethistoyourgrave.todos.ui.create.CreateTodoViewModel
import com.takethistoyourgrave.todos.ui.edit.EditTodoEvent
import com.takethistoyourgrave.todos.ui.edit.EditTodoViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ToDosNavigation() {
    val backStack = remember { mutableStateListOf<Any>(TodoListKey) }

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
                            backStack.add(CreateTodoKey)
                        }
                    )
                }

                is CreateTodoKey -> NavEntry(key) {
                    CreateTodoScreen(
                        onBack = {
                            backStack.removeLastOrNull()
                        },
                        onOpenColorPicker = { color ->
                            backStack.add(ColorPickerKey(initialColor = color, fromEdit = false))
                        }
                    )
                }

                is EditTodoKey -> NavEntry(key) {
                    EditTodoScreen(
                        uid = key.uid,
                        onBack = {
                            backStack.removeLastOrNull()
                        },
                        onOpenColorPicker = { color ->
                            backStack.add(ColorPickerKey(initialColor = color, fromEdit = true))
                        }
                    )
                }

                is ColorPickerKey -> NavEntry(key) {
                    val editViewModel: EditTodoViewModel = koinViewModel()
                    val createViewModel: CreateTodoViewModel = koinViewModel()
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

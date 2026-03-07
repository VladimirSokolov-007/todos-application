package com.takethistoyourgrave.todos.ui.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.takethistoyourgrave.todos.model.TodoItem
import com.takethistoyourgrave.todos.ui.components.TodoFormContent

@Composable
fun EditTodoScreen(
    viewModel: EditTodoViewModel,
    item: TodoItem,
    onBack: () -> Unit,
    onOpenColorPicker: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(item.uid) {
        viewModel.onEvent(EditTodoEvent.LoadItem(item))
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onBack()
    }

    TodoFormContent(
        title = "Редактирование",
        text = state.text,
        importance = state.importance,
        isDone = state.isDone,
        color = state.color,
        customColor = state.customColor,
        deadline = state.deadline,
        showDatePicker = state.showDatePicker,
        onTextChange = { viewModel.onEvent(EditTodoEvent.UpdateText(it)) },
        onImportanceChange = { viewModel.onEvent(EditTodoEvent.UpdateImportance(it)) },
        onIsDoneChange = { viewModel.onEvent(EditTodoEvent.UpdateIsDone(it)) },
        onColorChange = { viewModel.onEvent(EditTodoEvent.UpdateColor(it)) },
        onDeadlineChange = { viewModel.onEvent(EditTodoEvent.UpdateDeadline(it)) },
        onShowDatePicker = { viewModel.onEvent(EditTodoEvent.ShowDatePicker) },
        onHideDatePicker = { viewModel.onEvent(EditTodoEvent.HideDatePicker) },
        onSave = { viewModel.onEvent(EditTodoEvent.Save) },
        onBack = onBack,
        onOpenColorPicker = onOpenColorPicker
    )
}

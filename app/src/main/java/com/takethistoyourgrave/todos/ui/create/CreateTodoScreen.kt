package com.takethistoyourgrave.todos.ui.create

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.takethistoyourgrave.todos.ui.components.TodoFormContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CreateTodoScreen(
    onBack: () -> Unit,
    onOpenColorPicker: (Int) -> Unit,
    viewModel: CreateTodoViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) onBack()
    }

    TodoFormContent(
        title = "Новое дело",
        text = state.text,
        importance = state.importance,
        isDone = state.isDone,
        color = state.color,
        customColor = state.customColor,
        deadline = state.deadline,
        showDatePicker = state.showDatePicker,
        onTextChange = { viewModel.onEvent(CreateTodoEvent.UpdateText(it)) },
        onImportanceChange = { viewModel.onEvent(CreateTodoEvent.UpdateImportance(it)) },
        onIsDoneChange = { viewModel.onEvent(CreateTodoEvent.UpdateIsDone(it)) },
        onColorChange = { viewModel.onEvent(CreateTodoEvent.UpdateColor(it)) },
        onDeadlineChange = { viewModel.onEvent(CreateTodoEvent.UpdateDeadline(it)) },
        onShowDatePicker = { viewModel.onEvent(CreateTodoEvent.ShowDatePicker) },
        onHideDatePicker = { viewModel.onEvent(CreateTodoEvent.HideDatePicker) },
        onSave = { viewModel.onEvent(CreateTodoEvent.Save) },
        onBack = onBack,
        onOpenColorPicker = {
            onOpenColorPicker(state.customColor ?: state.color)
        }
    )
}

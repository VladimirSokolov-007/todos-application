package com.takethistoyourgrave.todos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.takethistoyourgrave.todos.domain.model.Importance
import com.takethistoyourgrave.todos.domain.model.TodoItem
import com.takethistoyourgrave.todos.domain.TodoRepository
import com.takethistoyourgrave.todos.navigation.ToDosNavigation
import com.takethistoyourgrave.todos.ui.theme.ToDosTheme
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {

    private val repository: TodoRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        if (repository.getItems().isEmpty()) {
            lifecycleScope.launch {
                repository.addItem(
                    TodoItem(
                        text = "Купить молоко",
                        importance = Importance.NORMAL,
                        isDone = true
                    )
                )
                repository.addItem(
                    TodoItem(
                        text = "Сдать лабу",
                        importance = Importance.HIGH,
                        deadline = LocalDateTime.now().plusDays(3)
                    )
                )
                repository.addItem(TodoItem(text = "Посмотреть фильм", importance = Importance.LOW))
            }
        }

        setContent {
            ToDosTheme {
                ToDosNavigation()
            }
        }
    }
}

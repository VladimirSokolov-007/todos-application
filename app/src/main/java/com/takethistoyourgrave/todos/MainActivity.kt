package com.takethistoyourgrave.todos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.takethistoyourgrave.todos.model.Importance
import com.takethistoyourgrave.todos.model.TodoItem
import com.takethistoyourgrave.todos.navigation.ToDosNavigation
import com.takethistoyourgrave.todos.ui.theme.ToDosTheme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = application as ToDosApp
        val storage = app.storage

        if (storage.getItems().isEmpty()) {
            storage.add(
                TodoItem(
                    text = "Купить молоко",
                    importance = Importance.NORMAL,
                    isDone = true
                )
            )
            storage.add(
                TodoItem(
                    text = "Сдать лабу",
                    importance = Importance.HIGH,
                    deadline = LocalDateTime.now().plusDays(3)
                )
            )
            storage.add(TodoItem(text = "Посмотреть фильм", importance = Importance.LOW))
            storage.save()
        }

        setContent {
            ToDosTheme {
                ToDosNavigation(storage)
            }
        }
    }
}
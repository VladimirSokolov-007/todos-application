package com.takethistoyourgrave.todos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.takethistoyourgrave.todos.data.FileStorage
import com.takethistoyourgrave.todos.model.Importance
import com.takethistoyourgrave.todos.model.TodoItem
import com.takethistoyourgrave.todos.ui.theme.ToDosTheme
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {

    private lateinit var storage: FileStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val leakyList = mutableListOf<ByteArray>()
        for (i in 0..300) {
            leakyList.add(ByteArray(1024 * 1024))
        }

        val file = File(filesDir, "todos.json")
        storage = FileStorage(file)
        storage.load()

        if(storage.getItems().isEmpty()) {
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items = storage.getItems()) { item ->
                            TodoItemCard(item = item)
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TodoItemCard(item: TodoItem) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = item.isDone,
                onCheckedChange = null
            )

            Column(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = item.text,
                    fontSize = 16.sp
                )

                if (item.deadline != null) {
                    Text(
                        text = "до ${item.deadline.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))}",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

package com.takethistoyourgrave.todos.data

import com.takethistoyourgrave.todos.model.TodoItem
import com.takethistoyourgrave.todos.model.json
import com.takethistoyourgrave.todos.model.parse
import org.json.JSONArray
import java.io.File
import java.time.LocalDateTime

class FileStorage(private val file: File) {

    private val items = mutableListOf<TodoItem>()

    fun getItems(): List<TodoItem> = items.toList()

    fun add(item: TodoItem) {
        items.add(item)
    }

    fun remove(uid: String) {
        items.removeAll { it.uid == uid }
    }

    fun save() {
        val jsonArray = JSONArray()
        for (item in items) {
            jsonArray.put(item.json)
        }
        file.writeText(jsonArray.toString())
    }

    fun load() {
        if (!file.exists()) return
        val text = file.readText()
        if (text.isBlank()) return

        val jsonArray = JSONArray(text)
        items.clear()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val item = TodoItem.parse(jsonObject)
            if (item != null) {
                items.add(item)
            }
        }
        removeExpired()
    }

    private fun removeExpired() {
        val now = LocalDateTime.now()
        items.removeAll {
            it.deadline != null && it.deadline.isBefore(now) && !it.isDone
        }
    }
}

package com.takethistoyourgrave.todos.data.local

import com.takethistoyourgrave.todos.domain.model.TodoItem
import com.takethistoyourgrave.todos.domain.model.json
import com.takethistoyourgrave.todos.domain.model.parse
import org.json.JSONArray
import timber.log.Timber
import java.io.File
import java.time.LocalDateTime

class FileStorage(private val file: File) {

    private val items = mutableListOf<TodoItem>()

    fun getItems(): List<TodoItem> = items.toList()

    fun add(item: TodoItem) {
        items.add(item)
        Timber.Forest.d("add: uid=${item.uid}, text=${item.text}")
    }

    fun remove(uid: String) {
        val removed = items.removeAll { it.uid == uid }
        if (removed) {
            Timber.Forest.d("remove: uid=$uid")
        } else {
            Timber.Forest.w("remove: uid=$uid не найден")
        }
    }

    fun save() {
        val jsonArray = JSONArray()
        for (item in items) {
            jsonArray.put(item.json)
        }
        file.writeText(jsonArray.toString())
        Timber.Forest.d("save: ${items.size} items в ${file.name}")
    }

    fun load() {
        if (!file.exists()) {
            Timber.Forest.d("load: файл ${file.name} не найден")
            return
        }
        val text = file.readText()
        if (text.isBlank()) {
            Timber.Forest.d("load: файл ${file.name} пуст")
            return
        }

        val jsonArray = JSONArray(text)
        items.clear()
        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val item = TodoItem.Companion.parse(jsonObject)
            if (item != null) {
                items.add(item)
            }
        }
        Timber.Forest.d("load: загружено ${items.size} items из ${file.name}")
        removeExpired()
    }

    private fun removeExpired() {
        val now = LocalDateTime.now()
        val before = items.size
        items.removeAll {
            it.deadline != null && it.deadline.isBefore(now) && !it.isDone
        }
        val removed = before - items.size
        if (removed > 0) {
            Timber.Forest.d("removeExpired: удалено $removed просроченных дел")
        }
    }
}
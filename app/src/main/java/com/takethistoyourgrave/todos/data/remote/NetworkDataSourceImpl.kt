package com.takethistoyourgrave.todos.data.remote

import com.takethistoyourgrave.todos.domain.model.TodoItem
import timber.log.Timber

class NetworkDataSourceImpl : NetworkDataSource {

    override fun loadItems(): List<TodoItem> {
        Timber.d("[Network] loadItems: запрос списка дел с бэкенда (заглушка)")
        return emptyList()
    }

    override fun loadItem(uid: String): TodoItem? {
        Timber.d("[Network] loadItem: запрос дела uid=$uid с бэкенда (заглушка)")
        return null
    }

    override fun sendItem(item: TodoItem) {
        Timber.d("[Network] sendItem: отправка дела uid=${item.uid}, text=${item.text} на бэкенд (заглушка)")
    }

    override fun deleteItem(uid: String) {
        Timber.d("[Network] deleteItem: удаление дела uid=$uid с бэкенда (заглушка)")
    }

    override fun sendAll(items: List<TodoItem>) {
        Timber.d("[Network] sendAll: отправка ${items.size} дел на бэкенд (заглушка)")
    }
}

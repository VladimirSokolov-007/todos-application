package com.takethistoyourgrave.todos.data

import com.takethistoyourgrave.todos.data.local.FileStorage
import com.takethistoyourgrave.todos.data.remote.NetworkDataSource
import com.takethistoyourgrave.todos.domain.model.TodoItem
import com.takethistoyourgrave.todos.domain.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

class TodoRepositoryImpl(
    private val storage: FileStorage,
    private val network: NetworkDataSource
) : TodoRepository {

    private val _itemsFlow = MutableStateFlow(storage.getItems())
    override val itemsFlow: Flow<List<TodoItem>> = _itemsFlow

    override fun getItems(): List<TodoItem> {
        return storage.getItems()
    }

    override fun getItem(uid: String): TodoItem? {
        return storage.getItems().find { it.uid == uid }
    }

    override fun addItem(item: TodoItem) {
        storage.add(item)
        storage.save()
        network.sendItem(item)
        _itemsFlow.value = storage.getItems()
        Timber.d("[Repo] addItem: uid=${item.uid}")
    }

    override fun updateItem(item: TodoItem) {
        storage.remove(item.uid)
        storage.add(item)
        storage.save()
        network.sendItem(item)
        _itemsFlow.value = storage.getItems()
        Timber.d("[Repo] updateItem: uid=${item.uid}")
    }

    override fun deleteItem(uid: String) {
        storage.remove(uid)
        storage.save()
        network.deleteItem(uid)
        _itemsFlow.value = storage.getItems()
        Timber.d("[Repo] deleteItem: uid=$uid")
    }

    override fun refresh() {
        val networkItems = network.loadItems()
        if (networkItems.isNotEmpty()) {
            Timber.d("[Repo] refresh: получено ${networkItems.size} дел с бэкенда")
        } else {
            Timber.d("[Repo] refresh: бэкенд пуст, используем кэш")
        }
        _itemsFlow.value = storage.getItems()
    }
}

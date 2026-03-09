package com.takethistoyourgrave.todos.data

import com.takethistoyourgrave.todos.data.local.TodoDao
import com.takethistoyourgrave.todos.data.remote.NetworkDataSource
import com.takethistoyourgrave.todos.domain.TodoRepository
import com.takethistoyourgrave.todos.domain.model.TodoItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class TodoRepositoryImpl(
    private val dao: TodoDao,
    private val network: NetworkDataSource,
    private val scope: CoroutineScope
) : TodoRepository {

    override val itemsFlow: Flow<List<TodoItem>> =
        dao.observeAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getItem(uid: String): TodoItem? {
        return dao.getById(uid)?.toDomain()
    }

    override suspend fun addItem(item: TodoItem) {
        dao.insert(item.toEntity())
        Timber.d("[Repo] addItem: uid=${item.uid} (saved to Room)")
        scope.launch {
            network.addItem(item)
            Timber.d("[Repo] addItem: uid=${item.uid} (synced to server)")
        }
    }

    override suspend fun updateItem(item: TodoItem) {
        dao.insert(item.toEntity())
        Timber.d("[Repo] updateItem: uid=${item.uid} (saved to Room)")
        scope.launch {
            network.updateItem(item)
            Timber.d("[Repo] updateItem: uid=${item.uid} (synced to server)")
        }
    }

    override suspend fun deleteItem(uid: String) {
        dao.deleteById(uid)
        Timber.d("[Repo] deleteItem: uid=$uid (deleted from Room)")
        scope.launch {
            network.deleteItem(uid)
            Timber.d("[Repo] deleteItem: uid=$uid (synced to server)")
        }
    }

    override suspend fun refresh() {
        val networkItems = network.loadItems()
        dao.deleteAll()
        dao.insertAll(networkItems.map { it.toEntity() })
        Timber.d("[Repo] refresh: синхронизировано ${networkItems.size} дел с сервера")
    }
}

package com.takethistoyourgrave.todos.data.remote

import com.takethistoyourgrave.todos.data.remote.dto.TodoElementRequest
import com.takethistoyourgrave.todos.data.remote.dto.TodoListRequest
import com.takethistoyourgrave.todos.domain.model.TodoItem
import timber.log.Timber

class NetworkDataSourceImpl(
    private val api: TodoApi,
    private val deviceId: String
) : NetworkDataSource {

    override var revision: Int = 0

    override suspend fun loadItems(): List<TodoItem> {
        return try {
            val response = api.getList()
            if (response.isSuccessful) {
                val body = response.body()!!
                revision = body.revision
                Timber.d("[Network] loadItems: получено ${body.list.size} дел, revision=$revision")
                body.list.map { it.toDomain() }
            } else {
                Timber.d("[Network] loadItems: ошибка ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Timber.d(e, "[Network] loadItems: исключение")
            emptyList()
        }
    }

    override suspend fun addItem(item: TodoItem): TodoItem? {
        return try {
            val request = TodoElementRequest(element = item.toDto(deviceId))
            val response = api.addItem(revision, request)
            if (response.isSuccessful) {
                val body = response.body()!!
                revision = body.revision
                Timber.d("[Network] addItem: uid=${item.uid}, revision=$revision")
                body.element.toDomain()
            } else {
                Timber.d("[Network] addItem: ошибка ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Timber.d(e, "[Network] addItem: исключение")
            null
        }
    }

    override suspend fun updateItem(item: TodoItem): TodoItem? {
        return try {
            val request = TodoElementRequest(element = item.toDto(deviceId))
            val response = api.updateItem(revision, item.uid, request)
            if (response.isSuccessful) {
                val body = response.body()!!
                revision = body.revision
                Timber.d("[Network] updateItem: uid=${item.uid}, revision=$revision")
                body.element.toDomain()
            } else {
                Timber.d("[Network] updateItem: ошибка ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Timber.d(e, "[Network] updateItem: исключение")
            null
        }
    }

    override suspend fun deleteItem(uid: String): TodoItem? {
        return try {
            val response = api.deleteItem(revision, uid)
            if (response.isSuccessful) {
                val body = response.body()!!
                revision = body.revision
                Timber.d("[Network] deleteItem: uid=$uid, revision=$revision")
                body.element.toDomain()
            } else {
                Timber.d("[Network] deleteItem: ошибка ${response.code()}")
                null
            }
        } catch (e: Exception) {
            Timber.d(e, "[Network] deleteItem: исключение")
            null
        }
    }

    override suspend fun patchAll(items: List<TodoItem>): List<TodoItem> {
        return try {
            val request = TodoListRequest(list = items.map { it.toDto(deviceId) })
            val response = api.patchList(revision, request)
            if (response.isSuccessful) {
                val body = response.body()!!
                revision = body.revision
                Timber.d("[Network] patchAll: отправлено ${items.size}, revision=$revision")
                body.list.map { it.toDomain() }
            } else {
                Timber.d("[Network] patchAll: ошибка ${response.code()}")
                emptyList()
            }
        } catch (e: Exception) {
            Timber.d(e, "[Network] patchAll: исключение")
            emptyList()
        }
    }
}

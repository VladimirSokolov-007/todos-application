package com.takethistoyourgrave.todos.data.remote

import com.takethistoyourgrave.todos.data.remote.dto.TodoElementRequest
import com.takethistoyourgrave.todos.data.remote.dto.TodoListRequest
import com.takethistoyourgrave.todos.domain.model.TodoItem
import kotlinx.coroutines.delay
import retrofit2.Response
import timber.log.Timber

class NetworkDataSourceImpl(
    private val api: TodoApi,
    private val deviceId: String
) : NetworkDataSource {

    override var revision: Int = 0

    private val retryDelays = longArrayOf(1_000, 2_000, 4_000, 8_000, 16_000)

    private suspend fun <T> retryOnError(
        tag: String,
        block: suspend () -> Response<T>
    ): Response<T>? {
        for (attempt in 0..retryDelays.size) {
            try {
                val response = block()
                if (response.isSuccessful) return response

                when (response.code()) {
                    500 -> {
                        if (attempt < retryDelays.size) {
                            Timber.d("[Network] $tag: 500, повтор через ${retryDelays[attempt]}мс (попытка ${attempt + 1})")
                            delay(retryDelays[attempt])
                            continue
                        }
                    }
                    400 -> {
                        Timber.d("[Network] $tag: 400 (unsynchronized), обновляю revision")
                        refreshRevision()
                        if (attempt < retryDelays.size) continue
                    }
                }
                Timber.d("[Network] $tag: ошибка ${response.code()}, попытки исчерпаны")
                return response
            } catch (e: Exception) {
                if (attempt < retryDelays.size) {
                    Timber.d(e, "[Network] $tag: исключение, повтор через ${retryDelays[attempt]}мс")
                    delay(retryDelays[attempt])
                } else {
                    Timber.d(e, "[Network] $tag: исключение, попытки исчерпаны")
                    return null
                }
            }
        }
        return null
    }

    private suspend fun refreshRevision() {
        try {
            val response = api.getList()
            if (response.isSuccessful) {
                revision = response.body()!!.revision
                Timber.d("[Network] refreshRevision: revision=$revision")
            }
        } catch (e: Exception) {
            Timber.d(e, "[Network] refreshRevision: исключение")
        }
    }

    override suspend fun loadItems(): List<TodoItem> {
        val response = retryOnError("loadItems") { api.getList() }
        if (response != null && response.isSuccessful) {
            val body = response.body()!!
            revision = body.revision
            Timber.d("[Network] loadItems: получено ${body.list.size} дел, revision=$revision")
            return body.list.map { it.toDomain() }
        }
        return emptyList()
    }

    override suspend fun addItem(item: TodoItem): TodoItem? {
        val response = retryOnError("addItem") {
            val request = TodoElementRequest(element = item.toDto(deviceId))
            api.addItem(revision, request)
        }
        if (response != null && response.isSuccessful) {
            val body = response.body()!!
            revision = body.revision
            Timber.d("[Network] addItem: uid=${item.uid}, revision=$revision")
            return body.element.toDomain()
        }
        return null
    }

    override suspend fun updateItem(item: TodoItem): TodoItem? {
        val response = retryOnError("updateItem") {
            val request = TodoElementRequest(element = item.toDto(deviceId))
            api.updateItem(revision, item.uid, request)
        }
        if (response != null && response.isSuccessful) {
            val body = response.body()!!
            revision = body.revision
            Timber.d("[Network] updateItem: uid=${item.uid}, revision=$revision")
            return body.element.toDomain()
        }
        return null
    }

    override suspend fun deleteItem(uid: String): TodoItem? {
        val response = retryOnError("deleteItem") {
            api.deleteItem(revision, uid)
        }
        if (response != null && response.isSuccessful) {
            val body = response.body()!!
            revision = body.revision
            Timber.d("[Network] deleteItem: uid=$uid, revision=$revision")
            return body.element.toDomain()
        }
        return null
    }

    override suspend fun patchAll(items: List<TodoItem>): List<TodoItem> {
        val response = retryOnError("patchAll") {
            val request = TodoListRequest(list = items.map { it.toDto(deviceId) })
            api.patchList(revision, request)
        }
        if (response != null && response.isSuccessful) {
            val body = response.body()!!
            revision = body.revision
            Timber.d("[Network] patchAll: отправлено ${items.size}, revision=$revision")
            return body.list.map { it.toDomain() }
        }
        return emptyList()
    }
}

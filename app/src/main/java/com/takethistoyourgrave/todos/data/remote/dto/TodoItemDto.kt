package com.takethistoyourgrave.todos.data.remote.dto

import android.graphics.Color
import com.google.gson.annotations.SerializedName
import com.takethistoyourgrave.todos.domain.model.Importance
import com.takethistoyourgrave.todos.domain.model.TodoItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

data class TodoItemDto(
    val id: String,
    val text: String,
    val importance: String,
    val deadline: Long? = null,
    val done: Boolean,
    val color: String? = null,
    @SerializedName("created_at") val createdAt: Long,
    @SerializedName("changed_at") val changedAt: Long,
    @SerializedName("last_updated_by") val lastUpdatedBy: String
) {
    fun toDomain(): TodoItem {
        return TodoItem(
            uid = id,
            text = text,
            importance = importanceFromApi(importance),
            color = color?.let { colorHexToInt(it) } ?: Color.WHITE,
            deadline = deadline?.let {
                LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneId.systemDefault())
            },
            isDone = done
        )
    }
}

private fun importanceFromApi(value: String): Importance = when (value) {
    "low" -> Importance.LOW
    "important" -> Importance.HIGH
    else -> Importance.NORMAL
}

private fun colorHexToInt(hex: String): Int {
    return Color.parseColor(hex)
}

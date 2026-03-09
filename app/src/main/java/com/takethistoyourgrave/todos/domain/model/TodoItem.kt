package com.takethistoyourgrave.todos.domain.model

import android.graphics.Color
import com.takethistoyourgrave.todos.data.remote.dto.TodoItemDto
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

data class TodoItem(
    val uid: String = UUID.randomUUID().toString(),
    val text: String,
    val importance: Importance,
    val color: Int = Color.WHITE,
    val deadline: LocalDateTime? = null,
    val isDone: Boolean = false
) {
    companion object

    fun toDto(deviceId: String): TodoItemDto {
        val now = System.currentTimeMillis() / 1000
        return TodoItemDto(
            id = uid,
            text = text,
            importance = importance.toApiString(),
            deadline = deadline?.atZone(ZoneId.systemDefault())?.toEpochSecond(),
            done = isDone,
            color = colorIntToHex(color),
            createdAt = now,
            changedAt = now,
            lastUpdatedBy = deviceId
        )
    }
}

private fun Importance.toApiString(): String = when (this) {
    Importance.LOW -> "low"
    Importance.NORMAL -> "basic"
    Importance.HIGH -> "important"
}

private fun colorIntToHex(color: Int): String {
    return String.format("#%06X", 0xFFFFFF and color)
}


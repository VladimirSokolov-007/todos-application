package com.takethistoyourgrave.todos.data.local

import android.graphics.Color
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.takethistoyourgrave.todos.domain.model.Importance
import com.takethistoyourgrave.todos.domain.model.TodoItem
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey val uid: String,
    val text: String,
    val importance: String = "NORMAL",
    val color: Int = Color.WHITE,
    val deadline: Long? = null,
    @ColumnInfo(name = "is_done") val isDone: Boolean = false,
    @ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis() / 1000,
    @ColumnInfo(name = "updated_at") val updatedAt: Long = System.currentTimeMillis() / 1000
) {
    fun toDomain(): TodoItem {
        return TodoItem(
            uid = uid,
            text = text,
            importance = try {
                Importance.valueOf(importance)
            } catch (e: Exception) {
                Importance.NORMAL
            },
            color = color,
            deadline = deadline?.let {
                LocalDateTime.ofInstant(Instant.ofEpochSecond(it), ZoneId.systemDefault())
            },
            isDone = isDone
        )
    }
}
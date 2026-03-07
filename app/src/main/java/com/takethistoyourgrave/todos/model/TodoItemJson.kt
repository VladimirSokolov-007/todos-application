package com.takethistoyourgrave.todos.model

import android.graphics.Color
import org.json.JSONObject
import java.time.LocalDateTime

fun TodoItem.Companion.parse(json: JSONObject): TodoItem? {
    return try {
        TodoItem(
            uid = json.getString("uid"),
            text = json.getString("text"),
            importance = if (json.has("importance")) {
                Importance.valueOf(json.getString("importance"))
            } else {
                Importance.NORMAL
            },
            color = if (json.has("color")) json.getInt("color") else Color.WHITE,
            deadline = if (json.has("deadline")) {
                LocalDateTime.parse(json.getString("deadline"))
            } else {
                null
            },
            isDone = json.optBoolean("isDone", false)
        )
    } catch (e: Exception) {
        null
    }
}

val TodoItem.json: JSONObject
    get() {
        val obj = JSONObject()
        obj.put("uid", uid)
        obj.put("text", text)

        if (importance != Importance.NORMAL) {
            obj.put("importance", importance.name)
        }

        if (color != Color.WHITE) {
            obj.put("color", color)
        }

        if (deadline != null) {
            obj.put("deadline", deadline.toString())
        }

        obj.put("isDone", isDone)
        return obj
    }

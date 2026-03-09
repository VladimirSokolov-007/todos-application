package com.takethistoyourgrave.todos.data.remote

import com.takethistoyourgrave.todos.data.remote.dto.TodoElementRequest
import com.takethistoyourgrave.todos.data.remote.dto.TodoElementResponse
import com.takethistoyourgrave.todos.data.remote.dto.TodoListRequest
import com.takethistoyourgrave.todos.data.remote.dto.TodoListResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TodoApi {

    @GET("list")
    suspend fun getList(): Response<TodoListResponse>

    @PATCH("list")
    suspend fun patchList(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body body: TodoListRequest
    ): Response<TodoListResponse>

    @POST("list")
    suspend fun addItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Body body: TodoElementRequest
    ): Response<TodoElementResponse>

    @PUT("list/{id}")
    suspend fun updateItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String,
        @Body body: TodoElementRequest
    ): Response<TodoElementResponse>

    @DELETE("list/{id}")
    suspend fun deleteItem(
        @Header("X-Last-Known-Revision") revision: Int,
        @Path("id") id: String
    ): Response<TodoElementResponse>
}

package com.takethistoyourgrave.todos.di

import android.os.Build
import com.takethistoyourgrave.todos.data.local.TodoDatabase
import com.takethistoyourgrave.todos.data.remote.NetworkDataSource
import com.takethistoyourgrave.todos.data.remote.NetworkDataSourceImpl
import com.takethistoyourgrave.todos.data.remote.TodoApi
import com.takethistoyourgrave.todos.data.TodoRepositoryImpl
import com.takethistoyourgrave.todos.domain.TodoRepository
import com.takethistoyourgrave.todos.ui.create.CreateTodoViewModel
import com.takethistoyourgrave.todos.ui.edit.EditTodoViewModel
import com.takethistoyourgrave.todos.ui.list.TodoListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://hive.mrdekk.ru/todo/"
private const val AUTH_TOKEN = "b75fe625-8bda-4f81-9837-09f573777ac1"

val appModule = module {

    single {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $AUTH_TOKEN")
                    .addHeader("X-Generate-Fails", "0")
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(logging)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TodoApi::class.java)
    }

    single { TodoDatabase.create(androidContext()) }
    single { get<TodoDatabase>().todoDao() }

    single<NetworkDataSource> {
        NetworkDataSourceImpl(
            api = get(),
            deviceId = Build.MODEL
        )
    }

    single { CoroutineScope(SupervisorJob() + Dispatchers.IO) }

    single<TodoRepository> { TodoRepositoryImpl(dao = get(), network = get(), scope = get()) }

    viewModel { TodoListViewModel(get()) }
    viewModel { CreateTodoViewModel(get()) }
    viewModel { EditTodoViewModel(get()) }
}

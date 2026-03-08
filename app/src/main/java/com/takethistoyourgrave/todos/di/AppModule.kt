package com.takethistoyourgrave.todos.di

import com.takethistoyourgrave.todos.data.CacheDataSource
import com.takethistoyourgrave.todos.data.FileStorage
import com.takethistoyourgrave.todos.data.NetworkDataSource
import com.takethistoyourgrave.todos.data.NetworkDataSourceImpl
import com.takethistoyourgrave.todos.data.TodoRepositoryImpl
import com.takethistoyourgrave.todos.domain.TodoRepository
import com.takethistoyourgrave.todos.ui.create.CreateTodoViewModel
import com.takethistoyourgrave.todos.ui.edit.EditTodoViewModel
import com.takethistoyourgrave.todos.ui.list.TodoListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import java.io.File

val appModule = module {

    single<CacheDataSource> {
        FileStorage(File(androidContext().filesDir, "todos.json")).also { it.load() }
    }

    single<NetworkDataSource> { NetworkDataSourceImpl() }

    single<TodoRepository> { TodoRepositoryImpl(cache = get(), network = get()) }

    viewModel { TodoListViewModel(get()) }
    viewModel { CreateTodoViewModel(get()) }
    viewModel { EditTodoViewModel(get()) }
}

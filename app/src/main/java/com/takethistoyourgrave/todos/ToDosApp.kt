package com.takethistoyourgrave.todos

import android.app.Application
import com.takethistoyourgrave.todos.data.FileStorage
import timber.log.Timber
import java.io.File

class ToDosApp : Application() {

    lateinit var storage: FileStorage

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        storage = FileStorage(File(filesDir, "todos.json"))
        storage.load()
    }
}

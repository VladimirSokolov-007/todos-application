package com.takethistoyourgrave.todos

import android.app.Application
import com.takethistoyourgrave.todos.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ToDosApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@ToDosApp)
            modules(appModule)
        }
    }
}

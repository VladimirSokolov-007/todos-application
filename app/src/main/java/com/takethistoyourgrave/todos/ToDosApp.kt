package com.takethistoyourgrave.todos

import android.app.Application
import timber.log.Timber

class ToDosApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}

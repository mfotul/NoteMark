package com.example.notemark

import android.app.Application
import com.example.notemark.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class NoteMarkApp: Application()  {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NoteMarkApp)
            modules(appModule)
        }
    }
}
package com.example.notemark.app

import android.app.Application
import com.example.notemark.core.data.networking.di.networkModule
import com.example.notemark.core.data.database.di.databaseModule
import com.example.notemark.app.di.appModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class NoteMarkApp: Application()  {

    val applicationScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@NoteMarkApp)
            modules(databaseModule, networkModule, appModule)
        }
    }
}
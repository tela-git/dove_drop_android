package com.example.dovedrop

import android.app.Application
import com.example.dovedrop.chat.data.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DoveDropApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@DoveDropApplication)
            modules(
                appModule
            )
        }
    }
}


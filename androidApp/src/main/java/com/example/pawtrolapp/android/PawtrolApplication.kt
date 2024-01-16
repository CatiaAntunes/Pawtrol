package com.example.pawtrolapp.android

import android.app.Application
import com.example.pawtrolapp.android.di.appModule
import com.example.pawtrolapp.di.getSharedModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PawtrolApplication: Application() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            modules(appModule + getSharedModules())
            androidContext(this@PawtrolApplication)
        }
    }
}
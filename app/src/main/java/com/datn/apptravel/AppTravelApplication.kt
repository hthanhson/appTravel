package com.datn.apptravel

import android.app.Application
import com.datn.apptravel.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AppTravelApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Koin dependency injection
        startKoin {
            androidContext(this@AppTravelApplication)
            modules(appModule)
        }
    }
}
package com.datn.apptravel

import android.app.Application
import com.datn.apptravel.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Custom Application class for initializing app-wide dependencies
 */
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
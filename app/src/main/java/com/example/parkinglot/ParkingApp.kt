package com.example.parkinglot

import android.app.Application
import com.example.parkinglot.domin.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ParkingApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ParkingApp)
            modules(appModule)
        }
    }
}

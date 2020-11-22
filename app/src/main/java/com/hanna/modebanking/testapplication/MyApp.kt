package com.hanna.modebanking.testapplication

import android.app.Application
import com.hanna.modebanking.testapplication.di.components.DaggerNetComponent
import com.hanna.modebanking.testapplication.di.components.NetComponent
import com.hanna.modebanking.testapplication.di.modules.AppModule
import com.hanna.modebanking.testapplication.di.modules.NetModule

class MyApp : Application() {

    lateinit var netComponent: NetComponent

    override fun onCreate() {
        super.onCreate()
        netComponent = DaggerNetComponent.builder()
            .appModule(AppModule(this))
            .netModule(NetModule())
            .build()
    }
}
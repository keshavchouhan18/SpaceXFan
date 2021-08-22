package com.spacexfanapplication

import android.app.Application
import android.content.Context
import com.spacexfanapplication.di.spaceXModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class SpaceXApplication : Application() {

    init {
        context = this
    }

    companion object {
        lateinit var context: SpaceXApplication
        fun getContext(): Context = context
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SpaceXApplication)
            modules(spaceXModules)
        }
    }
}
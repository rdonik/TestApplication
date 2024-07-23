package test.application.grafic

import android.app.Application
import android.content.Context

import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Application : Application() {

    override fun attachBaseContext(base: Context) {

        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
    }

}
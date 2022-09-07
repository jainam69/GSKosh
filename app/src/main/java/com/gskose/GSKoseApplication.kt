package com.gskose

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class GSKoseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

}
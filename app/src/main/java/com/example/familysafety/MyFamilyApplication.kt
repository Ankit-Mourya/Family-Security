package com.example.familysafety

import android.app.Application

class MyFamilyApplication:Application() {
    override fun onCreate() {
        super.onCreate()

        Sharedpref.init(this)
    }
}
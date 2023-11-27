package com.library.variants.test

import android.app.Application
import com.library.variants.demolib.HostApp

/**
 * @Author: Umer Siddique
 * @Date: 24/11/2023
 */

class App : Application() {

    private val context = this

    override fun onCreate() {
        super.onCreate()

        HostApp.init(context)
    }
}
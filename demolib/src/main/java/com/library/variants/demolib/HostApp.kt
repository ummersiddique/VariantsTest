package com.library.variants.demolib

import android.content.Context
import android.util.Log
import com.library.variants.demolib.network.Configs

/**
 * @Author: Umer Siddique
 * @Date: 24/11/2023
 */

class HostApp(context: Context?) {

    init {
        if (context != null) {
            Log.e(TAG, "Base url : ${Configs.BASE_URL}")
        }
    }

    companion object {

        private const val TAG = "HostApp"
        private lateinit var INSTANCE: HostApp

        @JvmStatic
        fun init(context: Context?) {
            if (::INSTANCE.isInitialized.not()) {
                INSTANCE = HostApp(context)
            }
        }
    }
}
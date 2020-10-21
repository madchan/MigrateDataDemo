package com.madchan.migratedatademo

import android.app.Application
import android.content.Context
import com.madchan.migratedatademo.util.LogUtil

class BaseApplication : Application() {

    init {
        mContext = this
        LogUtil.init(true)
    }

    companion object {
        lateinit var mContext: Context
        fun getContext() = mContext
    }
}
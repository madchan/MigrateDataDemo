package com.madchan.migratedatademo.activity

import android.app.Activity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.madchan.migratedatademo.R
import com.madchan.migratedatademo.dummy.DummyContent
import com.madchan.migratedatademo.manager.OldStorageManager
import com.madchan.migratedatademo.manager.ScopedStorageManager
import com.madchan.migratedatademo.manager.TestStorageManager
import com.madchan.migratedatademo.util.LogUtil
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_splash.*
import java.io.File
import java.util.concurrent.TimeUnit

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        OldStorageManager.copyFilesFromAssets(this)

//        TestStorageManager.init("kh123456")
//        TestStorageManager.migrateExistingFilesFromLegacyStorageDir(object : ScopedStorageManager.ProgressListener() {
//            override fun onStart() {
//                LogUtil.d("数据迁移开始: ")
//                progressBar.visibility = View.VISIBLE
//                migratingTipsTv.visibility = View.VISIBLE
//            }
//
//            override fun onProgress(progress: Long) {
//                LogUtil.d("数据正在迁移: $progress%")
//                progressBar.progress = progress.toInt()
//                migratingTipsTv.text = "正在为您将数据迁移至应用专属目录($progress%)"
//            }
//
//            override fun onFinish() {
//                LogUtil.d("数据迁移完成")
//                progressBar.visibility = View.GONE
//                migratingTipsTv.visibility = View.GONE
//                displaySplashView()
//            }
//
//            override fun onError() {
//                progressBar.visibility = View.GONE
//                migratingTipsTv.visibility = View.GONE
//                displaySplashView()
//            }
//        })

        displaySplashView()
    }

    private fun displaySplashView() {
        val splashFile = File(OldStorageManager.getSplashStorageDir(), DummyContent.SPLASH.path)
//        val splashFile = File(TestStorageManager.getSplashStorageDir(), DummyContent.SPLASH.path)
        Glide.with(this)
            .load(if(splashFile.exists()) splashFile else DummyContent.SPLASH.url)
//            .load(DummyContent.SPLASH.url)
            .centerCrop()
            .into(findViewById(R.id.splash))

        Observable.timer(3, TimeUnit.SECONDS)
            .subscribe(Consumer {
                MainActivity.startActivity(this)
                finish()
            })
    }

}
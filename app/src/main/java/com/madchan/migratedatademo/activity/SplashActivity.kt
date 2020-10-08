package com.madchan.migratedatademo.activity

import android.Manifest
import android.app.Activity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.madchan.migratedatademo.BaseApplication
import com.madchan.migratedatademo.R
import com.madchan.migratedatademo.constant.PreferencesKeys
import com.madchan.migratedatademo.manager.OldStorageManager
import com.madchan.migratedatademo.util.AssetsUtil
import com.madchan.migratedatademo.util.LibPreferencesUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import java.io.File
import java.util.concurrent.TimeUnit

class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            .subscribe { granted: Boolean? ->
                if (!granted!!) {
                } else {
                    if (!LibPreferencesUtil.getBoolean(
                            BaseApplication.getContext(),
                            PreferencesKeys.ALREADY_COPIED_FROM_ASSETS
                        )
                    ) {
                        AssetsUtil.copyFileFromAssets(
                            this,
                            "images/ic_avatar_spike.jpg",
                            File(OldStorageManager.getAvatarStorageDir(), "ic_avatar_spike.jpg")
                        )
                        AssetsUtil.copyFileFromAssets(
                            this,
                            "images/ic_avatar_jet.jpg",
                            File(OldStorageManager.getAvatarStorageDir(), "ic_avatar_jet.jpg")
                        )
                        AssetsUtil.copyFileFromAssets(
                            this,
                            "images/ic_angry.jpg",
                            File(OldStorageManager.getMessageThumbnailStorageDir(), "ic_angry.jpg")
                        )
                        AssetsUtil.copyFileFromAssets(
                            this,
                            "images/ic_green_pepper_shredded_pork.jpg",
                            File(
                                OldStorageManager.getMessageThumbnailStorageDir(),
                                "ic_green_pepper_shredded_pork.jpg"
                            )
                        )
                        AssetsUtil.copyFileFromAssets(
                            this,
                            "images/ic_tired.jpg",
                            File(OldStorageManager.getMessageThumbnailStorageDir(), "ic_tired.jpg")
                        )
                        AssetsUtil.copyFileFromAssets(
                            this,
                            "images/splash.webp",
                            File(OldStorageManager.getSplashStorageDir(), "splash.webp")
                        )
                        AssetsUtil.copyFileFromAssets(
                            this,
                            "audios/where_did_the_money_go.m4a",
                            File(OldStorageManager.getMessageAudioStorageDir(), "where_did_the_money_go.m4a")
                        )
                        LibPreferencesUtil.put(BaseApplication.getContext(), PreferencesKeys.ALREADY_COPIED_FROM_ASSETS, true)
                    }
                }
            }

        Glide.with(this)
            .load(File(OldStorageManager.getSplashStorageDir(), "splash.webp"))
            .centerCrop()
            .into(findViewById(R.id.splash))

        Observable.timer(3, TimeUnit.SECONDS)
            .subscribe(Consumer {
                MainActivity.startActivity(this)
                finish()
            })
    }

}
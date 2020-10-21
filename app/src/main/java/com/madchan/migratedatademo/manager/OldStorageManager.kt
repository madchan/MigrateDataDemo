package com.madchan.migratedatademo.manager

import android.Manifest
import android.app.Activity
import android.os.Environment
import com.madchan.migratedatademo.BaseApplication
import com.madchan.migratedatademo.constant.PreferencesKeys
import com.madchan.migratedatademo.util.AssetsUtil
import com.madchan.migratedatademo.util.LibPreferencesUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import java.io.File

/**
 * 旧版存储管理器
 * @author chenf
 */
class OldStorageManager {

    companion object {

        /**
         * 旧版存储位置，即位于/sdcard/下的Test目录
         */
        @JvmStatic
        fun getOldStorageRootDir() = File(Environment.getExternalStorageDirectory(), "Test")

        /**
         * 头像
         */
        @JvmStatic
        fun getAvatarStorageDir() : File{
            val file = File(getOldStorageRootDir(), "/Avatar")
            if (!file?.mkdirs()) {
            }
            return file
        }

        /**
         * 消息-缩略图
         * 包含图片、视频、<22k的音频、表情、定位、链接
         */
        @JvmStatic
        fun getMessageThumbnailStorageDir() : File{
            val file = File(getOldStorageRootDir(), "/Message/Thumbnail")
            if (!file?.mkdirs()) {
            }
            return file
        }

        /**
         * 消息-原图
         */
        @JvmStatic
        fun getMessageImageStorageDir() : File{
            val file = File(getOldStorageRootDir(), "/Message/Image")
            if (!file?.mkdirs()) {
            }
            return file
        }

        /**
         * 消息-语音
         */
        @JvmStatic
        fun getMessageAudioStorageDir() : File{
            val file = File(getOldStorageRootDir(), "/Message/Audio")
            if (!file?.mkdirs()) {
            }
            return file
        }

        /**
         * 消息-视频
         */
        @JvmStatic
        fun getMessageVideoStorageDir() : File{
            val file = File(getOldStorageRootDir(), "/Message/Video")
            if (!file?.mkdirs()) {
            }
            return file
        }

        /**
         * 闪屏图
         */
        @JvmStatic
        fun getSplashStorageDir()  : File{
            val file = File(getOldStorageRootDir(), "/Splash")
            if (!file?.mkdirs()) {
            }
            return file
        }

        @JvmStatic
        fun copyFilesFromAssets(activity: Activity) {
            RxPermissions(activity).request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted: Boolean? ->
                    if (!granted!!) {
                    } else {
                        if (!LibPreferencesUtil.getBoolean(
                                BaseApplication.getContext(),
                                PreferencesKeys.ALREADY_COPIED_FROM_ASSETS
                            )
                        ) {
                            AssetsUtil.copyFileFromAssets(
                                BaseApplication.getContext(),
                                "images/ic_avatar_spike.jpg",
                                File(OldStorageManager.getAvatarStorageDir(), "ic_avatar_spike.jpg")
                            )
                            AssetsUtil.copyFileFromAssets(
                                BaseApplication.getContext(),
                                "images/ic_avatar_jet.jpg",
                                File(OldStorageManager.getAvatarStorageDir(), "ic_avatar_jet.jpg")
                            )
                            AssetsUtil.copyFileFromAssets(
                                BaseApplication.getContext(),
                                "images/ic_angry.jpg",
                                File(OldStorageManager.getMessageThumbnailStorageDir(), "ic_angry.jpg")
                            )
                            AssetsUtil.copyFileFromAssets(
                                BaseApplication.getContext(),
                                "images/ic_green_pepper_shredded_pork.jpg",
                                File(
                                    OldStorageManager.getMessageThumbnailStorageDir(),
                                    "ic_green_pepper_shredded_pork.jpg"
                                )
                            )
                            AssetsUtil.copyFileFromAssets(
                                BaseApplication.getContext(),
                                "images/ic_tired.jpg",
                                File(OldStorageManager.getMessageThumbnailStorageDir(), "ic_tired.jpg")
                            )
                            AssetsUtil.copyFileFromAssets(
                                BaseApplication.getContext(),
                                "images/splash.webp",
                                File(OldStorageManager.getSplashStorageDir(), "splash.webp")
                            )
                            AssetsUtil.copyFileFromAssets(
                                BaseApplication.getContext(),
                                "audios/where_did_the_money_go.m4a",
                                File(OldStorageManager.getMessageAudioStorageDir(), "where_did_the_money_go.m4a")
                            )
                            AssetsUtil.copyFileFromAssets(
                                BaseApplication.getContext(),
                                "images/ic_reason_cover.jpg",
                                File(OldStorageManager.getMessageThumbnailStorageDir(), "ic_reason_cover.jpg")
                            )
                            AssetsUtil.copyFileFromAssets(
                                BaseApplication.getContext(),
                                "videos/reason.mp4",
                                File(OldStorageManager.getMessageVideoStorageDir(), "reason.mp4")
                            )
                            LibPreferencesUtil.put(BaseApplication.getContext(), PreferencesKeys.ALREADY_COPIED_FROM_ASSETS, true)
                        }
                    }
                }
        }
    }

}
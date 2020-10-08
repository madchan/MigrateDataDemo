package com.madchan.migratedatademo.manager

import android.os.Environment
import java.io.File

/**
 * Litalk存储管理器
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
    }

}
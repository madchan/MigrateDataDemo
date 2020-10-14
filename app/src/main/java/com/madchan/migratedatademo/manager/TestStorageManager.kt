package com.madchan.migratedatademo.manager

import com.madchan.migratedatademo.BaseApplication

class TestStorageManager {

    companion object {

        /** 子目录-公共 */
        private const val SUB_DIRECTORY_UNIVERSAL = "Universal"
        /** 子目录-特定用户 */
        private lateinit var SUB_DIRECTORY_SPECIFIC_USER : String

        @JvmStatic
        fun init(specificUser : String) {
            SUB_DIRECTORY_SPECIFIC_USER = specificUser
        }

        /**
         * 从旧版存储位置迁移现有文件
         */
        @JvmStatic
        fun migrateExistingFilesFromLegacyStorageDir(listener: ScopedStorageManager.ProgressListener) {
            // 旧版存储位置已不复存在，不需要处理
            if(!OldStorageManager.getOldStorageRootDir().exists()){
                listener.onFinish()
                return
            }

            // 列入需要迁移的旧版存储目录
            var map = linkedMapOf(
                    // 需要保留并迁移的目录
                    OldStorageManager.getAvatarStorageDir() to getAvatarStorageDir(),
                    OldStorageManager.getMessageThumbnailStorageDir() to getMessageThumbnailStorageDir(),
                    OldStorageManager.getMessageImageStorageDir() to getMessageImageStorageDir(),
                    OldStorageManager.getMessageAudioStorageDir() to getMessageAudioStorageDir(),
                    OldStorageManager.getMessageVideoStorageDir() to getMessageVideoStorageDir(),
                    // 不需要迁移直接删除的目录
                    OldStorageManager.getSplashStorageDir() to null
                    )

            // 最后移除应用的旧存储目录
            map[OldStorageManager.getOldStorageRootDir()] = null

            ScopedStorageManager.migrateExistingFilesFromLegacyStorageDir(map, listener)
        }

        /**
         * 头像
         */
        @JvmStatic
        fun getAvatarStorageDir() = ScopedStorageManager.getExternalStorageDir(
            BaseApplication.getContext(), null, "$SUB_DIRECTORY_UNIVERSAL/Avatar")

        /**
         * 消息-缩略图
         * 包含图片、视频、<22k的音频、表情、定位、链接
         */
        @JvmStatic
        fun getMessageThumbnailStorageDir() = ScopedStorageManager.getExternalStorageDir(
            BaseApplication.getContext(), null, "$SUB_DIRECTORY_UNIVERSAL/Message/Thumbnail")

        /**
         * 消息-原图
         */
        @JvmStatic
        fun getMessageImageStorageDir() = ScopedStorageManager.getExternalStorageDir(
            BaseApplication.getContext(), null, "$SUB_DIRECTORY_SPECIFIC_USER/Message/Image")

        /**
         * 消息-语音
         */
        @JvmStatic
        fun getMessageAudioStorageDir() = ScopedStorageManager.getExternalStorageDir(
            BaseApplication.getContext(), null, "$SUB_DIRECTORY_SPECIFIC_USER/Message/Audio")

        /**
         * 消息-视频
         */
        @JvmStatic
        fun getMessageVideoStorageDir() = ScopedStorageManager.getExternalStorageDir(
            BaseApplication.getContext(), null, "$SUB_DIRECTORY_SPECIFIC_USER/Message/Video")

        /**
         * 闪屏图
         */
        @JvmStatic
        fun getSplashStorageDir() = ScopedStorageManager.getExternalStorageDir(BaseApplication.getContext(), null, "$SUB_DIRECTORY_UNIVERSAL/Splash")
    }

}
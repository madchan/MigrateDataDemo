package com.madchan.migratedatademo.manager

import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer

class AudioPlayerManager {

    companion object {
        fun play(path: String): MediaPlayer {
            val audioAttributes = AudioAttributes.Builder()
                .setLegacyStreamType(AudioManager.STREAM_MUSIC)
                .build()

            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(path)
            mediaPlayer.setAudioAttributes(audioAttributes)
            mediaPlayer.prepare()
            mediaPlayer.start()
            return mediaPlayer
        }
    }
}
package com.madchan.migratedatademo.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.MediaController
import androidx.fragment.app.FragmentActivity
import com.madchan.migratedatademo.R
import kotlinx.android.synthetic.main.activity_video_play.*

class VideoPlayActivity : Activity() {

    companion object {
        fun startActivity(context: Context, url: String) {
            val intent = Intent(context, VideoPlayActivity::class.java)
            intent.apply {
                putExtra(Intent.EXTRA_STREAM, url)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_play)
        videoView.setVideoPath(intent.getStringExtra(Intent.EXTRA_STREAM))
        videoView.setMediaController(MediaController(this))
        videoView.start()
        videoView.requestFocus()
    }

}
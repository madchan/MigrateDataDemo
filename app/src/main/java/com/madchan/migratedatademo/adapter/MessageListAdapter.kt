package com.madchan.migratedatademo.adapter

import android.graphics.drawable.AnimationDrawable
import android.media.MediaPlayer
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.madchan.migratedatademo.R
import com.madchan.migratedatademo.activity.VideoPlayActivity
import com.madchan.migratedatademo.bean.AudioContent
import com.madchan.migratedatademo.bean.ImageContent
import com.madchan.migratedatademo.bean.Message
import com.madchan.migratedatademo.bean.VideoContent
import com.madchan.migratedatademo.constant.ContentType
import com.madchan.migratedatademo.constant.MessageType
import com.madchan.migratedatademo.manager.AudioPlayerManager
import com.madchan.migratedatademo.manager.OldStorageManager
import com.madchan.migratedatademo.manager.TestStorageManager
import com.madchan.migratedatademo.util.JSONUtil
import java.io.File

class MessageListAdapter(data: MutableList<Message>? = null) :
    BaseMultiItemQuickAdapter<Message, BaseViewHolder>(data) {

    init {
        addItemType(MessageType.SEND, R.layout.item_view_send_message);
        addItemType(MessageType.RECEIVE, R.layout.item_view_received_message);
    }

    override fun convert(holder: BaseViewHolder, item: Message) {
        holder.setText(R.id.nickname, item.nickname)
        Glide.with(context)
            .load(File(OldStorageManager.getAvatarStorageDir(), item.avatar))
//            .load(File(TestStorageManager.getAvatarStorageDir(), item.avatar))
            .centerCrop()
            .circleCrop()
            .into(holder.getView(R.id.avatar))

        holder.itemView.findViewById<View>(R.id.text_layout)?.apply { visibility = View.GONE }
        holder.itemView.findViewById<View>(R.id.audio_layout)?.apply { visibility = View.GONE }
        holder.itemView.findViewById<View>(R.id.thumbnail_layout)?.apply { visibility = View.GONE }

        // 根据返回的 type 分别设置数据
        when (item.type) {
            ContentType.TEXT -> convertText(holder, item)
            ContentType.IMAGE -> convertImage(holder, item)
            ContentType.AUDIO -> convertAudio(holder, item)
            ContentType.VIDEO -> convertVideo(holder, item)
        }
    }

    private fun convertText(holder: BaseViewHolder, item: Message) {
        val viewStub = holder.getView<ViewStub>(R.id.text_view_stub)
        val view: TextView =
            (if (viewStub.parent != null) viewStub.inflate() else holder.getView(R.id.text_layout)) as TextView
        view.visibility = View.VISIBLE
        view.text = item.content
    }

    private fun convertImage(holder: BaseViewHolder, item: Message) {
        val image = JSONUtil.fromJson(item.content, ImageContent::class.java)
        convertThumbnail(holder, image.thumbnail)
    }

    private fun convertAudio(holder: BaseViewHolder, item: Message) {
        val viewStub = holder.getView<ViewStub>(R.id.audio_view_stub)
        val view: ViewGroup =
            (if (viewStub.parent != null) viewStub.inflate() else holder.getView(R.id.audio_layout)) as ViewGroup
        view.visibility = View.VISIBLE
        val volume = view.findViewById<ImageView>(R.id.volume)
        val audio = JSONUtil.fromJson(item.content, AudioContent::class.java)
        view.setOnClickListener {
            volume.setImageResource(if (item.isReceived()) R.drawable.message_playaudio_blue_animlist else R.drawable.message_playaudio_white_animlist)
            (volume.drawable as AnimationDrawable).start()
            AudioPlayerManager.play(File(OldStorageManager.getMessageAudioStorageDir(), audio.compressed).absolutePath, MediaPlayer.OnCompletionListener {
//            AudioPlayerManager.play(File(TestStorageManager.getMessageAudioStorageDir(), audio.compressed).absolutePath, MediaPlayer.OnCompletionListener {
                if(volume.drawable is AnimationDrawable) (volume.drawable as AnimationDrawable).stop()
                volume.setImageResource(if(item.isReceived()) R.mipmap.message_ic_voice_blue_3 else R.mipmap.message_ic_voice_blue_r_3)
            })
        }
        volume.setImageResource(if(item.isReceived()) R.mipmap.message_ic_voice_blue_3 else R.mipmap.message_ic_voice_blue_r_3)
    }

    private fun convertVideo(holder: BaseViewHolder, item: Message) {
        val video = JSONUtil.fromJson(item.content, VideoContent::class.java)
        val view = convertThumbnail(holder, video.thumbnail)
        holder.setVisible(R.id.play_button, true)
        view.setOnClickListener {
            VideoPlayActivity.startActivity(context, File(OldStorageManager.getMessageVideoStorageDir(), video.compressed).absolutePath)
//            VideoPlayActivity.startActivity(context, File(TestStorageManager.getMessageVideoStorageDir(), video.compressed).absolutePath)
        }
    }

    private fun convertThumbnail(holder: BaseViewHolder, thumbnail: String) : View{
        val viewStub = holder.getView<ViewStub>(R.id.thumbnail_view_stub)
        val view =
            (if (viewStub.parent != null) viewStub.inflate() else holder.getView(R.id.thumbnail_layout)) as View
        view.visibility = View.VISIBLE
        val imageView = view.findViewById<ImageView>(R.id.thumbnail)
        Glide.with(context)
            .load(File(OldStorageManager.getMessageThumbnailStorageDir(), thumbnail))
//            .load(File(TestStorageManager.getMessageThumbnailStorageDir(), thumbnail))
            .override(500, 500)
            .centerCrop()
            .into(imageView)
        return view
    }

}
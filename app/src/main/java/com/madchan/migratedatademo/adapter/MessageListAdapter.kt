package com.madchan.migratedatademo.adapter

import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.madchan.migratedatademo.R
import com.madchan.migratedatademo.bean.AudioContent
import com.madchan.migratedatademo.bean.ImageContent
import com.madchan.migratedatademo.bean.Message
import com.madchan.migratedatademo.bean.VideoContent
import com.madchan.migratedatademo.constant.ContentType
import com.madchan.migratedatademo.constant.MessageType
import com.madchan.migratedatademo.manager.AudioPlayerManager
import com.madchan.migratedatademo.manager.OldStorageManager
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
            .centerCrop()
            .circleCrop()
            .into(holder.getView(R.id.avatar))

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
        val audio = JSONUtil.fromJson(item.content, AudioContent::class.java)
        view.setOnClickListener {
            AudioPlayerManager.play(File(OldStorageManager.getMessageAudioStorageDir(), audio.compressed).absolutePath)
        }
        holder.setImageResource(R.id.volume, if(item.isReceived()) R.mipmap.message_ic_voice_blue_3 else R.mipmap.message_ic_voice_blue_r_3)
    }

    private fun convertVideo(holder: BaseViewHolder, item: Message) {
        val video = JSONUtil.fromJson(item.content, VideoContent::class.java)
        convertThumbnail(holder, video.thumbnail)
        holder.setVisible(R.id.play_button, true)
    }

    private fun convertThumbnail(holder: BaseViewHolder, thumbnail: String) {
        val viewStub = holder.getView<ViewStub>(R.id.thumbnail_view_stub)
        val view =
            (if (viewStub.parent != null) viewStub.inflate() else holder.getView(R.id.thumbnail_layout)) as View
        val imageView = view.findViewById<ImageView>(R.id.thumbnail)
        Glide.with(context)
            .load(File(OldStorageManager.getMessageThumbnailStorageDir(), thumbnail))
            .override(500, 500)
            .centerCrop()
            .into(imageView)
    }

}
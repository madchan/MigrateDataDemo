package com.madchan.migratedatademo.adapter

import android.view.ViewStub
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.madchan.migratedatademo.R
import com.madchan.migratedatademo.bean.ImageContent
import com.madchan.migratedatademo.bean.Message
import com.madchan.migratedatademo.constant.ContentType
import com.madchan.migratedatademo.constant.MessageType
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
        }
    }

    private fun convertText(holder: BaseViewHolder, item: Message) {
        val viewStub = holder.getView<ViewStub>(R.id.text_view_stub)
        val view: TextView =
            (if (viewStub.parent != null) viewStub.inflate() else holder.getView(R.id.text_layout)) as TextView
        view.text = item.content
    }

    private fun convertImage(holder: BaseViewHolder, item: Message) {
        val viewStub = holder.getView<ViewStub>(R.id.thumbnail_view_stub)
        val view: ImageView =
            (if (viewStub.parent != null) viewStub.inflate() else holder.getView(R.id.thumbnail_layout)) as ImageView
        val image = JSONUtil.fromJson(item.content, ImageContent::class.java)
        Glide.with(context)
            .load(File(OldStorageManager.getMessageThumbnailStorageDir(), image.thumbnail))
            .override(500, 500)
            .centerCrop()
            .into(view)
    }


}
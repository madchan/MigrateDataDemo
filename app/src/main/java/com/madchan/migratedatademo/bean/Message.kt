package com.madchan.migratedatademo.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.madchan.migratedatademo.constant.MessageType
import com.madchan.migratedatademo.dummy.DummyContent

data class Message (val seq: Long,
                    val type: Int,
                    val fromId: String,
                    val toId: String,
                    val nickname: String,
                    val avatar: String,
                    val content: String
                   ) : MultiItemEntity{

    override val itemType = if(isReceived()) MessageType.RECEIVE else MessageType.SEND

    fun isReceived() = DummyContent.DUMMY_MY_ID != fromId
}
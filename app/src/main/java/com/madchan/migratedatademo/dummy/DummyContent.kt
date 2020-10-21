package com.madchan.migratedatademo.dummy

import com.madchan.migratedatademo.bean.*
import com.madchan.migratedatademo.constant.ContentType
import com.madchan.migratedatademo.util.JSONUtil
import java.util.*

object DummyContent {

    const val DUMMY_MY_ID = "100001"
    const val DUMMY_OTHER_ID = "100002"

    val SPLASH = Splash("splash.jpg", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602704749974&di=9683ea63fe7c49d960f8a894a8d66c3a&imgtype=0&src=http%3A%2F%2Fimglf5.nosdn.127.net%2Fimg%2FWTlOWGhKUlFmN1JsSmhTM2lSK0tQRnFJSkdmem5nb1IyQ1dENnhCbCs5NVc2bkYwS3JvM0xBPT0.jpg%3FimageView%26thumbnail%3D1680x0%26quality%3D96%26stripmeta%3D0%26type%3Djpg%257Cwatermark%26type%3D2%26text%3Dwqkg")
    val ITEMS: MutableList<Message> = ArrayList()

    init {
        ITEMS.add(
            Message(
                0,
                ContentType.TEXT,
                DUMMY_MY_ID,
                DUMMY_OTHER_ID,
                "Spike",
                "ic_avatar_spike.jpg",
                "Jet先生"
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.TEXT,
                DUMMY_MY_ID,
                DUMMY_OTHER_ID,
                "Spike",
                "ic_avatar_spike.jpg",
                "这盘里是青椒肉丝吧？"
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.IMAGE,
                DUMMY_MY_ID,
                DUMMY_OTHER_ID,
                "Spike",
                "ic_avatar_spike.jpg",
                JSONUtil.toJson(
                    ImageContent(
                        "ic_green_pepper_shredded_pork.jpg",
                        "ic_green_pepper_shredded_pork.jpg"
                    )
                )
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.TEXT,
                DUMMY_MY_ID,
                DUMMY_OTHER_ID,
                "Spike",
                "ic_avatar_spike.jpg",
                "那种里面没放肉的青椒肉丝"
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.TEXT,
                DUMMY_MY_ID,
                DUMMY_OTHER_ID,
                "Spike",
                "ic_avatar_spike.jpg",
                "应该不可以叫青椒肉丝吧"
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.IMAGE,
                DUMMY_MY_ID,
                DUMMY_OTHER_ID,
                "Spike",
                "ic_avatar_spike.jpg",
                JSONUtil.toJson(
                    ImageContent(
                        "ic_tired.jpg",
                        "ic_tired.jpg"
                    )
                )
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.TEXT,
                DUMMY_OTHER_ID,
                DUMMY_MY_ID,
                "Jet",
                "ic_avatar_jet.jpg",
                "不！可以叫的"
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.TEXT,
                DUMMY_MY_ID,
                DUMMY_OTHER_ID,
                "Spike",
                "ic_avatar_spike.jpg",
                "没人这样叫的！！"
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.IMAGE,
                DUMMY_MY_ID,
                DUMMY_OTHER_ID,
                "Spike",
                "ic_avatar_spike.jpg",
                JSONUtil.toJson(
                    ImageContent(
                        "ic_angry.jpg",
                        "ic_angry.jpg"
                    )
                )
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.TEXT,
                DUMMY_OTHER_ID,
                DUMMY_MY_ID,
                "Jet",
                "ic_avatar_jet.jpg",
                "没钱的时候就可以这样叫"
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.AUDIO,
                DUMMY_MY_ID,
                DUMMY_OTHER_ID,
                "Spike",
                "ic_avatar_spike.jpg",
                JSONUtil.toJson(
                    AudioContent(
                        "where_did_the_money_go.m4a"
                    )
                )
            )
        )
        ITEMS.add(
            Message(
                0,
                ContentType.VIDEO,
                DUMMY_OTHER_ID,
                DUMMY_MY_ID,
                "Jet",
                "ic_avatar_jet.jpg",
                JSONUtil.toJson(
                    VideoContent(
                        "ic_reason_cover.jpg",
                        "reason.mp4"
                    )
                )
            )
        )
    }

}
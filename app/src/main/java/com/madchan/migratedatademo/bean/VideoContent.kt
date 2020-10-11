package com.madchan.migratedatademo.bean

data class VideoContent(override val thumbnail: String, override val compressed: String) : MediaContent(thumbnail, compressed, null){
}
package com.madchan.migratedatademo.bean

//data class ImageContent(override val thumbnail: String, override val compressed: String) : MediaContent(thumbnail, compressed, null){
data class ImageContent(val thumbnail: String, val compressed: String){
}
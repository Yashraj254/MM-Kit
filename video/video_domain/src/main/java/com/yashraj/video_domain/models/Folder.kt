package com.yashraj.video_domain.models

data class Folder(
    val mediaCount:Int,
    val modified:Long,
    val name:String,
    val path: String,
    val size:Long
)

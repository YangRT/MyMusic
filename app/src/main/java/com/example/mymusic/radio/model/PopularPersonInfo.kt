package com.example.mymusic.radio.model

data class PopularPersonInfo(val code: Int, val msg: String, val data: PopularPersonData)

data class PopularPersonData(val total: Int, val updateTime: Long, val list: List<PopularPerson>)

data class PopularPerson(val id: Long, val rank: Int, val nickName: String, val avatarUrl: String, val userFollowedCount: Int, val userType: Int)
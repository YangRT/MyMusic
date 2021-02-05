package com.example.mymusic.search.identity

data class Response(val code: Int, val desc: String, val data: List<IdentityData>)

data class IdentityData(val song: String, val singer: String)
package com.example.mymusic.radio.model


data class CategoryListInfo(val code: Int, val hasMore: Boolean, val djRadios: List<DjRadio>, val count: Int)

data class DjRadio(val subCount: Int, val category: String, val picUrl: String, val desc: String, val name: String, val id: Long)

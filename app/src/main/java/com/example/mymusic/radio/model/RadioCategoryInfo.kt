package com.example.mymusic.radio.model

data class RadioCategoryInfo(val code: Int, val categories: List<RadioCategory>)

data class RadioCategory(val name: String, val id: Int, val pic56x56Url: String)


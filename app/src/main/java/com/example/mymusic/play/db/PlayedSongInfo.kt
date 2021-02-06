package com.example.mymusic.play.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PlayedRecord")
data class PlayedSongInfo(@PrimaryKey var id: String, var name: String, var artist: String, var duration: Long, var songCover: String)
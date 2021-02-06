package com.example.mymusic.play.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mymusic.MyApplication

@Database(entities = [PlayedSongInfo::class],version = 1)
abstract class PlayedSongDatabase: RoomDatabase() {

    abstract fun playedSongDao(): PlayedSongDao

    companion object{
        val instance = Single.sin
    }

    private object Single{
        val sin: PlayedSongDatabase = Room.databaseBuilder(
            MyApplication.context,
            PlayedSongDatabase::class.java,
            "song.db"
        ).allowMainThreadQueries().build()
    }
}
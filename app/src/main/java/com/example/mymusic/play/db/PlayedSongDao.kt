package com.example.mymusic.play.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class PlayedSongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(info: PlayedSongInfo)

    @Query("select * from PlayedRecord")
    abstract fun queryAll():List<PlayedSongInfo>
}
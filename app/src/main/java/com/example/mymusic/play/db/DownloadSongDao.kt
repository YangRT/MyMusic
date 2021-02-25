package com.example.mymusic.play.db

import androidx.room.*

@Dao
abstract class DownloadSongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(info: DownloadSongInfo)

    @Query("select * from DownloadMusic")
    abstract fun queryAll():List<DownloadSongInfo>

    @Delete
    abstract fun delete(info: DownloadSongInfo)

    @Query("select * from DownloadMusic where id = :songId")
    abstract fun query(songId: String): List<DownloadSongInfo>
}
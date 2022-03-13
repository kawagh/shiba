package com.example.shiba

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommitDao {
    @Query("SELECT * FROM commits")
    fun getAll(): LiveData<List<Commit>>

    @Query("DELETE FROM commits")
    fun deleteAll(): Unit

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(commit: Commit)
}
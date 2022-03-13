package com.example.shiba

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date
import java.time.LocalDate

@Entity(tableName = "commits")
data class Commit(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val date: String,
)

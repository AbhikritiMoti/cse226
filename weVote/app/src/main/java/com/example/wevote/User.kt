package com.example.wevote

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long  = 0,
    val username: String,
    val email: String,
    val password: String,
    val dob: String
)

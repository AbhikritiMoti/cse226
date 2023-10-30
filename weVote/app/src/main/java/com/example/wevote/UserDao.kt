package com.example.wevote

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)


    @Query("SELECT * FROM user_table")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT * FROM user_table WHERE username = :username AND password = :password")
    fun checkLogin(username: String, password: String): User?

}

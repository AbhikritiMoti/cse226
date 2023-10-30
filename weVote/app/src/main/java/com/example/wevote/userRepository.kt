package com.example.wevote

import androidx.lifecycle.LiveData

class userRepository(private val userDao: UserDao) {

    val readAllData: LiveData<List<User>> = userDao.readAllData()

    fun addUser(user : User){
        userDao.addUser(user)
    }

    fun checkLogin(username: String, password: String): User? {
        return userDao.checkLogin(username, password)
    }

}
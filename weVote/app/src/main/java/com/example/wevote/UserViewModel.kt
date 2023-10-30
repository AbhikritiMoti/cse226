package com.example.wevote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application){

    val readAllData: LiveData<List<User>>
    private val repository: userRepository

    init {
        val userDao = Userdatabase.getDatabase(application).UserDao()
        repository = userRepository(userDao)
        readAllData = repository.readAllData

    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun checkLogin(username: String, password: String): LiveData<Boolean> {
        val loginResultLiveData = MutableLiveData<Boolean>()

        viewModelScope.launch(Dispatchers.IO) {
            val user = repository.checkLogin(username, password)
            val isLoginSuccessful = user != null

            // Update the LiveData on the main thread
            withContext(Dispatchers.Main) {
                loginResultLiveData.value = isLoginSuccessful
            }
        }

        return loginResultLiveData
    }

}
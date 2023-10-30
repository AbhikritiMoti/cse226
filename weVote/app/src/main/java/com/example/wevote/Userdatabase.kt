package com.example.wevote

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class Userdatabase : RoomDatabase(){

    abstract fun UserDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE : Userdatabase? = null

        fun getDatabase(context : Context): Userdatabase{
            val tempInstance = INSTANCE
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    Userdatabase::class.java,
                    "user_database"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}
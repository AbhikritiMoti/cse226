package com.example.wevote

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class drawer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("username", null)

        if (username != null) {
            // Set the retrieved username in the TextView
            val unameTextView = findViewById<TextView>(R.id.uName)
            unameTextView.text = "Welcome, $username"
        } else {
            Log.e("NavigationDrawerActivity", "Username not found in shared preferences")
        }



    }
}
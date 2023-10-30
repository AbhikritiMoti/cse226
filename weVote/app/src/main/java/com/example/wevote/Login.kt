package com.example.wevote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class Login : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var userViewModel: UserViewModel

    private lateinit var cld : ConnectionLiveData
    private lateinit var layout1 : ImageView
    private lateinit var layout2 : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val registerCv = findViewById<View>(R.id.register_cv)
        registerCv.setOnClickListener {
            val intent = Intent(this, Signup::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()

        checkNetworkConnection()

        layout1 = findViewById(R.id.wifiON)
        layout2 = findViewById(R.id.wifiOFF)



        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        emailEditText = findViewById(R.id.email_edittext_login)
        passwordEditText = findViewById(R.id.password_edittext_login)

        val loginButton = findViewById<Button>(R.id.login_button_login)
        loginButton.setOnClickListener {

            val username = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Check if the username and password exist in the database
            if (username.isEmpty() || password.isEmpty()) {
                Log.d("Login", "Login Fail")
                val vg: ViewGroup? = findViewById(R.id.custom_toast)
                val inflater = layoutInflater
                val layout: View = inflater.inflate(R.layout.custom_toast, vg)
                val tv = layout.findViewById<TextView>(R.id.txtVw)
                tv.text = "Username and password cannot \n             be empty."
                val toast = Toast(applicationContext)
                toast.setGravity(Gravity.BOTTOM, 0, 300)
                toast.setView(layout)
                toast.duration = Toast.LENGTH_SHORT
                toast.show()
            } else {
                userViewModel.checkLogin(username, password).observe(this, Observer { isSuccessful ->
                    if (isSuccessful) {
                        // Your login success logic here
                        Log.d("Login", "Login Success")
                        val vg: ViewGroup? = findViewById(R.id.custom_toast)
                        val inflater = layoutInflater
                        val layout: View = inflater.inflate(R.layout.custom_toast, vg)
                        val tv = layout.findViewById<TextView>(R.id.txtVw)
                        tv.text = "Login Success"
                        val toast = Toast(applicationContext)
                        toast.setGravity(Gravity.BOTTOM, 0, 300)
                        toast.setView(layout)
                        toast.duration = Toast.LENGTH_SHORT
                        toast.show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("username", username) // "username" is the key
                        startActivity(intent)
                    } else {
                        // Your login failure logic here
                        Log.d("Login", "Login Fail")
                        val vg: ViewGroup? = findViewById(R.id.custom_toast)
                        val inflater = layoutInflater
                        val layout: View = inflater.inflate(R.layout.custom_toast, vg)
                        val tv = layout.findViewById<TextView>(R.id.txtVw)
                        tv.text = "Invalid Credentials"
                        val toast = Toast(applicationContext)
                        toast.setGravity(Gravity.BOTTOM, 0, 300)
                        toast.setView(layout)
                        toast.duration = Toast.LENGTH_SHORT
                        toast.show()
                    }
                })
            }

        }

    }

    private fun checkNetworkConnection() {
        cld = ConnectionLiveData(application)

        cld.observe(this, { isConnected ->

            if (isConnected){

                layout1.visibility = View.VISIBLE
                layout2.visibility = View.GONE

            }else{
                layout1.visibility = View.GONE
                layout2.visibility = View.VISIBLE
            }

        })
    }

}
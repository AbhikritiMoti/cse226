package com.example.wevote

import android.app.DatePickerDialog
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
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

class Signup : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var dobEditText: EditText
    private lateinit var idEditText: EditText

    private lateinit var cld : ConnectionLiveData
    private lateinit var layout1 : ImageView
    private lateinit var layout2 : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        checkNetworkConnection()

        layout1 = findViewById(R.id.wifiON)
        layout2 = findViewById(R.id.wifiOFF)



        val loginTextView = findViewById<TextView>(R.id.login_text)
        loginTextView.setOnClickListener {

            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        val dob = findViewById<EditText>(R.id.dob)
        dob.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                this,
                { view, year, monthOfYear, dayOfMonth ->

                    val dat = (dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year)
                    dob.setText(dat)
                },

                year,
                month,
                day
            )
            datePickerDialog.show()
        }

        val loginCv = findViewById<View>(R.id.login_cv)
        loginCv.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }

        supportActionBar?.hide()


        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)


        usernameEditText = findViewById(R.id.name_edittext_signup)
        emailEditText = findViewById(R.id.email_edittext_signup)
        passwordEditText = findViewById(R.id.password_edittext_signup)
        dobEditText = findViewById(R.id.dob)
        idEditText = findViewById(R.id.id_edittext_signup)

        val signupButton = findViewById<Button>(R.id.register_button_signup)
        signupButton.setOnClickListener {
            // Get user input from EditText fields
            val id = idEditText.text.toString()
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val dob = dobEditText.text.toString()

            if (id.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty() || dob.isEmpty()) {
                Log.d("Login", "Login Fail")
                val vg: ViewGroup? = findViewById(R.id.custom_toast)
                val inflater = layoutInflater
                val layout: View = inflater.inflate(R.layout.custom_toast, vg)
                val tv = layout.findViewById<TextView>(R.id.txtVw)
                tv.text = "Please fill in all the fields."
                val toast = Toast(applicationContext)
                toast.setGravity(Gravity.BOTTOM, 0, 300)
                toast.setView(layout)
                toast.duration = Toast.LENGTH_SHORT
                toast.show()

            } else {
                // Create a User object
                val id = idEditText.text.toString().toLong()
                val user = User(
                    id = id,
                    username = username,
                    email = email,
                    password = password,
                    dob = dob
                )

                // Add the user to the Room database using the UserViewModel
                userViewModel.addUser(user)
                Log.d("Database", "User added: $user")

                Log.d("Login", "Login Fail")
                val vg: ViewGroup? = findViewById(R.id.custom_toast)
                val inflater = layoutInflater
                val layout: View = inflater.inflate(R.layout.custom_toast, vg)
                val tv = layout.findViewById<TextView>(R.id.txtVw)
                tv.text = "Signup successful!."
                val toast = Toast(applicationContext)
                toast.setGravity(Gravity.BOTTOM, 0, 300)
                toast.setView(layout)
                toast.duration = Toast.LENGTH_SHORT
                toast.show()
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
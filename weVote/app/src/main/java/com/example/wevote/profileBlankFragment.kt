package com.example.wevote

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider


class profileBlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_blank, container, false)
        // Initialize the UserViewModel
        var userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Find the TextView elements in your XML layout using 'view'
        val userIDTextView = view.findViewById<TextView>(R.id.userID)
        val userNameTextView = view.findViewById<TextView>(R.id.userName)
        val userEmailTextView = view.findViewById<TextView>(R.id.userEmail)
        val userPasswordTextView = view.findViewById<TextView>(R.id.userPassword)
        val userDOBTextView = view.findViewById<TextView>(R.id.userDOB)

        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { users ->
            // Assuming you want to display data of the first user in the list (you can modify this part)
            if (!users.isEmpty()) {
                val user = users[0]
                // Populate the TextView elements with user data
                userIDTextView.text = user.id.toString()
                userNameTextView.text = user.username
                userEmailTextView.text = user.email
                userPasswordTextView.text = user.password
                userDOBTextView.text = user.dob
            }
        })




        return inflater.inflate(R.layout.fragment_profile_blank, container, false)
    }

}
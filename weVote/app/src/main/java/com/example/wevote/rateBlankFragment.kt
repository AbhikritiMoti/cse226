package com.example.wevote

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class rateBlankFragment : Fragment() {

    private lateinit var feedbackEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var recentReviewTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_rate_blank, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        feedbackEditText = view.findViewById(R.id.feedback)
        submitButton = view.findViewById(R.id.submitButton)
        recentReviewTextView = view.findViewById(R.id.recentH)


        submitButton.setOnClickListener {
            val feedbackText = feedbackEditText.text.toString()
            recentReviewTextView.text = feedbackText

            val vg: ViewGroup? = view?.findViewById(R.id.custom_toast)

            val inflater = layoutInflater
            val layout: View = inflater.inflate(R.layout.custom_toast, vg)
            val tv = layout.findViewById<TextView>(R.id.txtVw)
            tv.text = "Submitted"
            val toast = Toast(context)
            toast.setGravity(Gravity.BOTTOM, 0, 300)
            toast.view = layout
            toast.duration = Toast.LENGTH_SHORT
            toast.show()

        }
    }
}
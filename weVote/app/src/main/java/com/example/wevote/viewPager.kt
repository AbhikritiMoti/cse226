package com.example.wevote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import android.content.Intent

class viewPager : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: ViewPagerAdapter
    private var currentFragmentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        supportActionBar?.hide()

        viewPager = findViewById(R.id.viewPagerr)
        adapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        val prevButton = findViewById<Button>(R.id.prev)
        val nextButton = findViewById<Button>(R.id.next)


        prevButton.setOnClickListener {
            if (currentFragmentPosition > 0) {
                currentFragmentPosition--
                viewPager.currentItem = currentFragmentPosition
            }
        }

        nextButton.setOnClickListener {
            if (currentFragmentPosition < adapter.count - 1) {
                currentFragmentPosition++
                viewPager.currentItem = currentFragmentPosition
            } else {
                // Navigate back to the main activity
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }
        }

        // Initially, disable the "Prev" button on the first fragment
        prevButton.isEnabled = false

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                // Update the current fragment position
                currentFragmentPosition = position

                // Enable/disable "Prev" button based on the current position
                prevButton.isEnabled = currentFragmentPosition > 0

                // Check if it's the last fragment and update the "Next" button text
                if (currentFragmentPosition == adapter.count - 1) {
                    nextButton.text = "Let's Go"
                } else {
                    nextButton.text = "Next"
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
            }
        })
    }
}

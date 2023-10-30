package com.example.wevote

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(fm:FragmentManager) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 3;
    }

    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> {
                return ViewPagerFragment1()
            }
            1 -> {
                return ViewPagerFragment2()
            }
            2 -> {
                return ViewPagerFragment3()
            }
            else -> {
                return ViewPagerFragment1()
            }
        }
    }


}

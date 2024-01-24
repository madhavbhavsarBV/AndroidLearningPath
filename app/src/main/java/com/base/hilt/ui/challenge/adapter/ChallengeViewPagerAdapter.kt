package com.base.hilt.ui.challenge.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ChallengeViewPagerAdapter(fa: FragmentActivity, private val list: ArrayList<Fragment>) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return list[0]
            1 -> return list[1]
            2 -> return list[2]
            3 -> return list[3]

        }
        return list[0]
    }
}
package com.base.hilt.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeViewPagerAdapter(fa: FragmentActivity, private val list: List<Fragment>) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return list[0]
            1 -> return list[1]
            2 -> return list[2]

        }
        return list[0]
    }
}
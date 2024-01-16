package com.base.hilt.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.base.hilt.R

class OnBoardingViewPagerAdapter: RecyclerView.Adapter<OnBoardingViewPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_on_boarding_first, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Customize the views based on the position
        when (position) {
            0 -> holder.bindView(R.layout.layout_on_boarding_first)
            1 -> holder.bindView(R.layout.layout_on_boarding_second)
        }
    }

    override fun getItemCount(): Int {
        // Return the total number of views
        return 2
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(layoutResId: Int) {
            // Inflate and bind the layout resource to the view
            val customView = LayoutInflater.from(itemView.context).inflate(layoutResId, null)
            (itemView as ViewGroup).removeAllViews()
            (itemView as ViewGroup).addView(customView)
        }
    }
}
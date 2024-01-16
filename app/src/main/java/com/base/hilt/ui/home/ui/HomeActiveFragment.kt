package com.base.hilt.ui.home.ui

import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentHomeActiveBinding
import com.base.hilt.ui.home.viewmodel.HomeViewModel


class HomeActiveFragment : FragmentBase<HomeViewModel,FragmentHomeActiveBinding >(){
    override fun getLayoutId(): Int = R.layout.fragment_home_active

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(isBottomNavVisible = true, type = 2, isVisible = true))
    }

    override fun initializeScreenVariables() {
//        TODO("Not yet implemented")
    }

    override fun getViewModelClass(): Class<HomeViewModel> = HomeViewModel::class.java

}
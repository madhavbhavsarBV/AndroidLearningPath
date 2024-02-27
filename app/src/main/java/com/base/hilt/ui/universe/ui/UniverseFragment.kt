package com.base.hilt.ui.universe.ui

import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.base.ViewModelBase
import com.base.hilt.databinding.FragmentUniverseBinding


class UniverseFragment : FragmentBase<ViewModelBase, FragmentUniverseBinding>(){
    override fun getLayoutId(): Int = R.layout.fragment_universe

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(isBottomNavVisible = true, type = 2, isVisible = true))
    }

    override fun initializeScreenVariables() {

    }

    override fun getViewModelClass(): Class<ViewModelBase> =ViewModelBase::class.java

}
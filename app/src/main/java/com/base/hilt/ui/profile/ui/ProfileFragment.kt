package com.base.hilt.ui.profile.ui

import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.base.ViewModelBase
import com.base.hilt.databinding.FragmentProfileBinding


class ProfileFragment : FragmentBase<ViewModelBase, FragmentProfileBinding>() {
    override fun getLayoutId(): Int =R.layout.fragment_profile

    override fun setupToolbar() = viewModel.setToolbarItems(ToolbarModel())

    override fun initializeScreenVariables() {

    }

    override fun getViewModelClass(): Class<ViewModelBase> = ViewModelBase::class.java
}
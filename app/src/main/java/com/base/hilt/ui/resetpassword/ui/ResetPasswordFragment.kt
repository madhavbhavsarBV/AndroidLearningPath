package com.base.hilt.ui.resetpassword.ui

import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.base.ViewModelBase
import com.base.hilt.databinding.FragmentResetPasswordBinding


class ResetPasswordFragment : FragmentBase<ViewModelBase, FragmentResetPasswordBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_reset_password

    override fun setupToolbar() {
        viewModel.setToolbarItems(
            ToolbarModel(
                isVisible = true,
                title = "Reset Password",
                backBtnVisible = true,
                loginVisible = false,
                isBottomNavVisible = false
            )
        )
    }

    override fun initializeScreenVariables() {
//        TODO("Not yet implemented")
    }

    override fun getViewModelClass(): Class<ViewModelBase> = ViewModelBase::class.java


}
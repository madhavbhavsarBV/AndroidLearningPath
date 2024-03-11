package com.base.hilt.ui.settings.ui

import androidx.navigation.fragment.findNavController
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ToolbarModel
import com.base.hilt.databinding.FragmentSettingsBinding
import com.base.hilt.ui.settings.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment() : FragmentBase<SettingsViewModel,FragmentSettingsBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun setupToolbar() {
        viewModel.setToolbarItems(ToolbarModel(
            isVisible= true,
            backBtnVisible = true,
            isBottomNavVisible = false,
            title = getString(R.string.settings),
            type = 3
        ))
    }

    override fun initializeScreenVariables() {

        getDataBinding().viewmodel = viewModel

        observeData()

    }

    private fun observeData() {

        viewModel.onBtnEditProfileClick?.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.editProfileFragment)
        }
        viewModel.onBtnEditProfileClick?.observe(viewLifecycleOwner){
            findNavController().navigate(R.id.updatePasswordFragment)
        }


    }

    override fun getViewModelClass(): Class<SettingsViewModel> = SettingsViewModel::class.java

}

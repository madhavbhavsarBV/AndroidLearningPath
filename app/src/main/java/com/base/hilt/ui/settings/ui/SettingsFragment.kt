package com.base.hilt.ui.settings.ui

import android.util.Log
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.databinding.FragmentSettingsBinding
import com.base.hilt.ui.settings.viewmodel.SettingsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment() : FragmentBase<SettingsViewModel,FragmentSettingsBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun setupToolbar() {

    }

    override fun initializeScreenVariables() {
//        curFrag("Settings")
//        Log.i("currrfrag", "initializeScreenVariables: ${currentFragName}")
    }

    override fun getViewModelClass(): Class<SettingsViewModel> = SettingsViewModel::class.java

}

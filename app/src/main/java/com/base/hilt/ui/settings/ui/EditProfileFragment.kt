package com.base.hilt.ui.settings.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.databinding.FragmentEditProfileBinding
import com.base.hilt.ui.settings.viewmodel.SettingsViewModel


class EditProfileFragment : FragmentBase<SettingsViewModel, FragmentEditProfileBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_edit_profile

    override fun setupToolbar() {
//        TODO("Not yet implemented")
    }

    override fun initializeScreenVariables() {

//        TODO("Not yet implemented")
    }

    override fun getViewModelClass(): Class<SettingsViewModel> = SettingsViewModel::class.java
}
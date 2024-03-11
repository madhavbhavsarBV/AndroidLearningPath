package com.base.hilt.ui.settings.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.hilt.R
import com.base.hilt.base.FragmentBase
import com.base.hilt.base.ViewModelBase
import com.base.hilt.databinding.FragmentUpdatePasswordBinding
import com.base.hilt.ui.settings.viewmodel.SettingsViewModel


class UpdatePasswordFragment : FragmentBase<ViewModelBase,FragmentUpdatePasswordBinding>() {
    override fun getLayoutId(): Int = R.layout.fragment_update_password

    override fun setupToolbar() {

    }

    override fun initializeScreenVariables() {

    }

    override fun getViewModelClass(): Class<ViewModelBase> = ViewModelBase::class.java

}
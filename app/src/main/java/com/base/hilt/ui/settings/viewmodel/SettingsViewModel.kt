package com.base.hilt.ui.settings.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.repository.AuthRepository
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(val repository: AuthRepository) : ViewModelBase() {

    val onBtnEditProfileClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()
    var onBtnChangePasswordClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun editProfileClick() {
        onBtnEditProfileClick?.call()
    }

    fun changePasswordClick() {
        onBtnChangePasswordClick?.call()
    }


}
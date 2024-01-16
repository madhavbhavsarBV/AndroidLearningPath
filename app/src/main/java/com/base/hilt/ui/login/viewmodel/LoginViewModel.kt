package com.base.hilt.ui.login.viewmodel

import com.base.hilt.R
import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.PrefKey
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel


class LoginViewModel:ViewModelBase() {


    var onBtnLoginClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()
    var onBtnForgotPasswordClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun btnLoginClick() {
        onBtnLoginClick?.call()
    }

    fun btnForgotPasswordClick() {
        onBtnForgotPasswordClick?.call()
    }


}
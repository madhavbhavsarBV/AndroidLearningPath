package com.base.hilt.ui.forgotpassword.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.SingleLiveEvent

class ForgotPasswordViewModel :ViewModelBase() {

    var btnSendOtpClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun btnSendOtpClick(){
        btnSendOtpClick?.call()
    }


}
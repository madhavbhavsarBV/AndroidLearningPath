package com.base.hilt.ui.signup.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.SingleLiveEvent

class CreateAccountViewModel:ViewModelBase() {

    var onBtnNextClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()


    fun onBtnNextClick(){
        onBtnNextClick?.call()
    }

}
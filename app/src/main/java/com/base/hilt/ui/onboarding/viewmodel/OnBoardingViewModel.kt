package com.base.hilt.ui.onboarding.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.SingleLiveEvent

class OnBoardingViewModel:ViewModelBase() {

    var onBtnNextClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()


    fun onBtnNextClick(){
        onBtnNextClick?.call()
    }

}
package com.base.hilt.ui.challenge.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.SingleLiveEvent

class ChallengeViewModel:ViewModelBase() {


    var onBtnNextClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()


    fun onBtnNextClick(){
        onBtnNextClick?.call()
    }

}
package com.base.hilt.ui.challenge.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.SingleLiveEvent

class ChallengeDetailViewModel:ViewModelBase() {

    var acceptedByClick:SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun acceptedByClick(){
        acceptedByClick?.call()
    }
    var endByClick:SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun endByClick(){
        endByClick?.call()
    }

}
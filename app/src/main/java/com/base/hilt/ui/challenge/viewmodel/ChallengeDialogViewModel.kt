package com.base.hilt.ui.challenge.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.SingleLiveEvent

class ChallengeDialogViewModel: ViewModelBase() {

    var onBtnCreateChallengeClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun onBtnCreateChallengeClick() {
        onBtnCreateChallengeClick?.call()
    }

    var onCancelClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun onCancelClick() {
        onCancelClick?.call()
    }

}
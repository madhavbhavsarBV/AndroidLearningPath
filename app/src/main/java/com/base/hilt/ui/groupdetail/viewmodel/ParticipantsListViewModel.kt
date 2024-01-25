package com.base.hilt.ui.groupdetail.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.SingleLiveEvent

class ParticipantsListViewModel:ViewModelBase() {
    var onBackClick : SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun onBackClick(){
        onBackClick?.call()
    }

}
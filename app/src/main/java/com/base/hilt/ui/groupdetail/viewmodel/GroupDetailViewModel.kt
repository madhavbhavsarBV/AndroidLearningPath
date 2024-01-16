package com.base.hilt.ui.groupdetail.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.SingleLiveEvent

class GroupDetailViewModel: ViewModelBase() {

    var onCommentClick : SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun onCommentClick(){
        onCommentClick?.call()
    }
    var onBackClick : SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun onBackClick(){
        onBackClick?.call()
    }
}
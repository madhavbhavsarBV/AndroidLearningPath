package com.base.hilt.ui.account.viewmodel

import com.base.hilt.base.ViewModelBase
import com.base.hilt.utils.SingleLiveEvent

class AccountViewModel:ViewModelBase() {

    var onLogOutClick :SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun onLogoutClick(){
        onLogOutClick?.call()
    }
}
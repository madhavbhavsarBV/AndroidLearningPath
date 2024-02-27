package com.base.hilt.ui.account.viewmodel

import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.LogoutMutation
import com.base.hilt.UserDataQuery
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.repository.AuthRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    val repository: AuthRepository
) : ViewModelBase() {

    var onLogOutClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()
    var onLogOutLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<LogoutMutation.Data>>>()

    fun onLogoutClick() {
        onLogOutClick?.call()
    }

    fun onLogoutApi() {
        viewModelScope.launch {
            onLogOutLiveData.postValue(ResponseHandler.Loading)
            val res = repository.onLogoutApi()
            onLogOutLiveData.postValue(res)
        }
    }

    val userDataLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<UserDataQuery.Data>>>()

    fun onUserDataApiCall() {
        viewModelScope.launch {
            userDataLiveData.postValue(ResponseHandler.Loading)
            val res = repository.userData()
            userDataLiveData.postValue(res)
        }
    }

}
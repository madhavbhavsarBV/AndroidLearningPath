package com.base.hilt.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.LoginMutation
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.repository.AuthRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.LoginInput
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModelBase() {


    val onBtnSignUpClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()
    var onBtnLoginClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()
    var onBtnForgotPasswordClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()

    val loginLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<LoginMutation.Data>>?>()



    fun btnLoginClick() {
        onBtnLoginClick?.call()
        Log.i("madmad", "observeData: vm 0")
    }

    fun btnSignUpClick() {
        onBtnSignUpClick?.call()
    }


    fun loginApi(loginReq: LoginInput) {
        Log.i("mad2", "loginApi: vm called")
        viewModelScope.launch {
            loginLiveData.postValue(ResponseHandler.Loading)
            val result = authRepository.onLoginApi(loginReq)
            loginLiveData.postValue(result)
        }
    }

    fun btnForgotPasswordClick() {
        onBtnForgotPasswordClick?.call()
    }


}
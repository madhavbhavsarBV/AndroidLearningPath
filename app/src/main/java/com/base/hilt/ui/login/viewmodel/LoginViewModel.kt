package com.base.hilt.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.LoginMutation
import com.base.hilt.R
import com.base.hilt.base.ViewModelBase
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.LoginInput
import com.base.hilt.ui.login.repository.LoginRepository
import com.base.hilt.utils.PrefKey
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModelBase() {


    var onBtnLoginClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()
    var onBtnForgotPasswordClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()

//    private val _loginResponse = MutableLiveData<ResponseHandler<ResponseData<LoginResponse>?>>()
//    val loginResponse: LiveData<ResponseHandler<ResponseData<LoginResponse>?>> = _loginResponse


    private val _loginLiveData = MutableLiveData<ResponseHandler<ApolloResponse<LoginMutation.Data>>>()
    val loginLiveData: LiveData<ResponseHandler<ApolloResponse<LoginMutation.Data>>> = _loginLiveData


    fun btnLoginClick() {
        onBtnLoginClick?.call()
        Log.i("madmad", "observeData: vm 0")
    }



    fun loginApi(loginReq : LoginInput){
        Log.i("mad2", "loginApi: vm called")
        viewModelScope.launch {

            _loginLiveData.postValue(ResponseHandler.Loading)
            try {
                val result =  loginRepository.onLoginApi(loginReq)
                _loginLiveData.postValue(result)
            } catch (e: Exception) {
                _loginLiveData.postValue( ResponseHandler.OnFailed(0,"exception ${e}","0"))
            }
        }
    }

    fun btnForgotPasswordClick() {
        onBtnForgotPasswordClick?.call()
    }


}
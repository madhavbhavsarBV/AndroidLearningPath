package com.base.hilt.ui.forgotpassword.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.base.hilt.ForgotPasswordMutation
import com.base.hilt.LoginMutation
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.repository.AuthRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ForgotPasswordInput
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val authRepository: AuthRepository
):ViewModelBase() {

    init {
        Log.i("madmad", "observeData: vm runing")
    }
    var btnSendOtpClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()


    private val _forgotPasswordLiveData = MutableLiveData<ResponseHandler<ApolloResponse<ForgotPasswordMutation.Data>>>()
    val forgotPasswordLiveData: LiveData<ResponseHandler<ApolloResponse<ForgotPasswordMutation.Data>>> = _forgotPasswordLiveData

    fun btnSendOtpClick(){
        btnSendOtpClick?.call()

    }

    fun forgotPasswordApi(forgotPasswordInput: ForgotPasswordInput){
        viewModelScope.launch {
            _forgotPasswordLiveData.postValue(ResponseHandler.Loading)
            try {
                val response = authRepository.onForgotPasswordApi(forgotPasswordInput)
                _forgotPasswordLiveData.postValue(response)
            } catch (e:Exception){

            }


        }
    }

}
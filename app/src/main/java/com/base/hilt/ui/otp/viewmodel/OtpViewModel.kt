package com.base.hilt.ui.otp.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.ResendSmsOtpMutation
import com.base.hilt.VerifySmsOtpMutation
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.repository.AuthRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.OtpInput
import com.base.hilt.type.ResendSmsOtpInput
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(val repository: AuthRepository) : ViewModelBase(){

    var onResendClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()
    var onEditClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()
    var onBtnContinueClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()


    val otpVerificationLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<VerifySmsOtpMutation.Data>>>()
    val resendOtpLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<ResendSmsOtpMutation.Data>>>()


    fun resendOtpClick(){
        onResendClick?.call()
    }
    fun btnContinueClick(){
        onBtnContinueClick?.call()
    }

    fun otpVerifyApi(input : OtpInput){
        Log.i("mad2", "loginApi: vm called")
        viewModelScope.launch {
            otpVerificationLiveData.postValue(ResponseHandler.Loading)
            val result =  repository.onVerifyOtpApi(input)
            otpVerificationLiveData.postValue(result)
        }
    }
    fun resendOtpApi(input : ResendSmsOtpInput){
        Log.i("mad2", "loginApi: vm called")
        viewModelScope.launch {
            resendOtpLiveData.postValue(ResponseHandler.Loading)
            val result =  repository.onResendOtp(input)
            resendOtpLiveData.postValue(result)
        }
    }

    fun onEditBtnClick(){
        onEditClick?.call()
    }


}
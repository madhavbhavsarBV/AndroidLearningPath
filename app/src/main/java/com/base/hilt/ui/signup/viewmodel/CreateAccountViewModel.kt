package com.base.hilt.ui.signup.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.LoginMutation
import com.base.hilt.SignupMutation
import com.base.hilt.base.ViewModelBase
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.LoginInput
import com.base.hilt.type.SignUpInput
import com.base.hilt.ui.signup.repository.CreateAccountRepository
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(val repository: CreateAccountRepository):ViewModelBase() {

    var onBtnNextClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()


    fun onBtnNextClick(){
        onBtnNextClick?.call()
    }


    private val _signUpLiveData = MutableLiveData<ResponseHandler<ApolloResponse<SignupMutation.Data>>>()
    val signUpLiveData: LiveData<ResponseHandler<ApolloResponse<SignupMutation.Data>>> = _signUpLiveData

    fun signUpApi(signUpInput : SignUpInput){
        Log.i("mad2", "loginApi: vm called")
        viewModelScope.launch {

            _signUpLiveData.postValue(ResponseHandler.Loading)
            try {
                val result =  repository.onSignUpApi(signUpInput)
                _signUpLiveData.postValue(result)
            } catch (e: Exception) {
                _signUpLiveData.postValue( ResponseHandler.OnFailed(0,"exception ${e}","0"))
            }
        }
    }


}
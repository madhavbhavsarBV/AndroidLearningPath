package com.base.hilt.ui.challenge.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.model.ChallengeRequestModel
import com.base.hilt.domain.repository.UserRepository
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.challenge.model.ChallengeModel
import com.base.hilt.ui.challenge.model.ContactsModel
import com.base.hilt.ui.challenge.model.ContactsRequest
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor (private val userRepository: UserRepository):ViewModelBase() {

    var onBtnNextClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun onBtnNextClick(){
        onBtnNextClick?.call()
    }

    var createChallengeLiveData = SingleLiveEvent<ResponseHandler<ResponseData<ChallengeModel>?>>()
    fun callCreateChallenge(request: RequestBody) {
        Log.i("restapi", "observerData: here1")
        createChallengeLiveData.postValue(ResponseHandler.Loading)
        viewModelScope.launch {
            val response = userRepository.createChallengeApi(request)
            createChallengeLiveData.postValue(response)
        }
    }

    var contactsLiveData = SingleLiveEvent<ResponseHandler<ResponseData<ContactsModel>?>>()
    fun callContactsApi(request: ContactsRequest) {
        Log.i("restapi2", "observerData: here1")
        contactsLiveData.postValue(ResponseHandler.Loading)
        viewModelScope.launch {
            val response = userRepository.contactsApi(request)
            contactsLiveData.postValue(response)
        }
    }

}
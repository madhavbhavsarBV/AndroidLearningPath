package com.base.hilt.ui.challenge.viewmodel

import androidx.lifecycle.viewModelScope
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.repository.UserRepository
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.challenge.model.ChallengeModel
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ChallengeViewModel @Inject constructor (val userRepository: UserRepository):ViewModelBase() {


    var onBtnNextClick: SingleLiveEvent<Boolean>? = SingleLiveEvent()


    fun onBtnNextClick(){
        onBtnNextClick?.call()
    }

    var createChallengeLiveData = SingleLiveEvent<ResponseHandler<ResponseData<ChallengeModel>?>>()
    fun callCreateChallenge(request: RequestBody) {
        createChallengeLiveData.postValue(ResponseHandler.Loading)
        viewModelScope.launch {
            val response = userRepository.callCreateChallenge(request)
            createChallengeLiveData.postValue(response)
        }
    }

}
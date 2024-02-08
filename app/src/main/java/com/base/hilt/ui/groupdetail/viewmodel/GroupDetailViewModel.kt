package com.base.hilt.ui.groupdetail.viewmodel

import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.ChallengeDetailQuery
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.repository.DashboardRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupDetailViewModel @Inject constructor(val repository: DashboardRepository): ViewModelBase() {

    var onCommentClick : SingleLiveEvent<Boolean>? = SingleLiveEvent()

    fun onCommentClick(){
        onCommentClick?.call()
    }

    val challengeDetailLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<ChallengeDetailQuery.Data>>>()
    fun challengeDetailApiCall(uuid:String){
        viewModelScope.launch {
            challengeDetailLiveData.postValue(ResponseHandler.Loading)
            val response = repository.challengeDetailApi(uuid)
            challengeDetailLiveData.postValue(response)
        }
    }
}
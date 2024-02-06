package com.base.hilt.ui.home.viewmodel

import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.ChallengeListQuery
import com.base.hilt.ChallengeListingCountQuery
import com.base.hilt.UnreadNotificationCountQuery
import com.base.hilt.UserDataQuery
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.repository.DashboardRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ChallengeListInput
import com.base.hilt.ui.home.repository.HomeRepository
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val repository: DashboardRepository) : ViewModelBase() {

    val userDataLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<UserDataQuery.Data>>?>()
    fun userDataApiCall() {
        viewModelScope.launch {
            userDataLiveData.postValue(ResponseHandler.Loading)
            val response = repository.userData()
            userDataLiveData.postValue(response)
        }
    }

    val challengeListingCountLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<ChallengeListingCountQuery.Data>>?>()
    fun challengeListingCountApiCall(){
        viewModelScope.launch {
            challengeListingCountLiveData.postValue(ResponseHandler.Loading)
            val response = repository.challengeListingCount()
            challengeListingCountLiveData.postValue(response)
        }
    }

    val unreadNotificationCountLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<UnreadNotificationCountQuery.Data>>?>()
    fun unreadNotificationCountApiCall(){
        viewModelScope.launch {
            unreadNotificationCountLiveData.postValue(ResponseHandler.Loading)
            val response = repository.unreadNotificationCount()
            unreadNotificationCountLiveData.postValue(response)
        }

    }

    val challengeListLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<ChallengeListQuery.Data>>?>()
    fun challengeListApiCall(input:ChallengeListInput){
        viewModelScope.launch {
            challengeListLiveData.postValue(ResponseHandler.Loading)
            val response = repository.challengeListApi(input)
            challengeListLiveData.postValue(response)
        }

    }

}
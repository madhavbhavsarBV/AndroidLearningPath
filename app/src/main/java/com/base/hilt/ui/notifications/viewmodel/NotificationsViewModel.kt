package com.base.hilt.ui.notifications.viewmodel

import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.MarkNotificationReadMutation
import com.base.hilt.NotificationsListQuery
import com.base.hilt.UnreadNotificationCountQuery
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.repository.DashboardRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.MarkNotificationReadInput
import com.base.hilt.type.NotificationListInput
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(val repository: DashboardRepository) : ViewModelBase() {


    val notificationListLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<NotificationsListQuery.Data>>>()
     fun notificationListApiCall(input : NotificationListInput){
        viewModelScope.launch {
            notificationListLiveData.postValue(ResponseHandler.Loading)
            val res = repository.notificationListApi(input)
            notificationListLiveData.postValue(res)
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
    val markNotificationReadLiveData = SingleLiveEvent<ResponseHandler<ApolloResponse<MarkNotificationReadMutation.Data>>?>()
    fun markNotificationReadApiCall(input : MarkNotificationReadInput){
        viewModelScope.launch {
            markNotificationReadLiveData.postValue(ResponseHandler.Loading)
            val response = repository.markNotificationReadApi(input)
            markNotificationReadLiveData.postValue(response)
        }

    }

}
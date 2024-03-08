package com.base.hilt

import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.base.ViewModelBase
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModelBase() {
    var unreadNotificationCountLiveData =
        MutableLiveData<ResponseHandler<ApolloResponse<UnreadNotificationCountQuery.Data>>>()



}
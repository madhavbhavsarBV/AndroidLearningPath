package com.base.hilt.ui.splash.viewmodel

import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.ConfigurationQuery
import com.base.hilt.base.ViewModelBase
import com.base.hilt.domain.model.ConfigInput
import com.base.hilt.domain.repository.AuthRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(val repository: AuthRepository) : ViewModelBase() {

    val configLiveData =
        SingleLiveEvent<ResponseHandler<ApolloResponse<ConfigurationQuery.Data>>?>()

    fun callConfigApi(input:ConfigInput) {

        viewModelScope.launch {
            configLiveData.postValue(ResponseHandler.Loading)
            val response = repository.onConfigApi(input)
            configLiveData.postValue(response)

        }


    }

}
package com.base.hilt.ui.universe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.UserDataQuery
import com.base.hilt.base.ViewModelBase
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.universe.repository.UniverseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UniverseViewModel @Inject constructor(val universeRepository: UniverseRepository) : ViewModelBase() {

    var apiCallLiveData = MutableLiveData<ResponseHandler<ApolloResponse<UserDataQuery.Data>>>()

    fun apiCall(){
        viewModelScope.launch{
               universeRepository.getApiCallResponse().collect{
                   apiCallLiveData.value = it
               }
        }
    }

}
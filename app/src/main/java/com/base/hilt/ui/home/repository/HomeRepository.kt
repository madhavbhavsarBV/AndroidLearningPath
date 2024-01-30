package com.base.hilt.ui.home.repository

import com.apollographql.apollo3.ApolloClient
import com.base.hilt.base.BaseRepository
import com.base.hilt.network.ApiInterface
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apolloClient: ApolloClient) :
    BaseRepository() {

//    suspend fun callHomeScreenAPI(): ResponseHandler<ResponseData<HomeScreenVendorsListResponse>?> {
//            return makeAPICall {
//                apiInterface.callHomeScreenApiGetVendors("R", 29.3759, 47.9774)
//            }
//    }
}
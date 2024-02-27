package com.base.hilt.ui.home.repository

import com.apollographql.apollo3.ApolloClient
import com.base.hilt.base.BaseRepository
import javax.inject.Inject

class HomeRepository @Inject constructor(apolloClient: ApolloClient) :
    BaseRepository() {

//    suspend fun callHomeScreenAPI(): ResponseHandler<ResponseData<HomeScreenVendorsListResponse>?> {
//            return makeAPICall {
//                apiInterface.callHomeScreenApiGetVendors("R", 29.3759, 47.9774)
//            }
//    }
}
package com.base.hilt.ui.universe.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.UserDataQuery
import com.base.hilt.base.BaseRepository
import com.base.hilt.network.ResponseHandler
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class UniverseRepository @Inject constructor(val apolloClient: ApolloClient): BaseRepository() {

    suspend fun getApiCallResponse() : Flow<ResponseHandler<ApolloResponse<UserDataQuery.Data>>>{
       return graphQlApiCallWithFlow {
           apolloClient.query(UserDataQuery()).execute()
       }
    }

}
package com.base.hilt.domain.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.ChallengeListQuery
import com.base.hilt.UnreadNotificationCountQuery
import com.base.hilt.base.BaseRepository
import com.base.hilt.network.ResponseHandler
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : BaseRepository() {

    suspend fun callUnreadNotificationCount(): ResponseHandler<ApolloResponse<UnreadNotificationCountQuery.Data>?>{
        return graphQlApiCall {
            apolloClient.query(UnreadNotificationCountQuery()).execute()
        }
    }

}
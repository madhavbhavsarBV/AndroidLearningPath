package com.base.hilt.domain.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.ChallengeListQuery
import com.base.hilt.UnreadNotificationCountQuery
import com.base.hilt.base.BaseRepository
import com.base.hilt.network.ApiInterface
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.challenge.model.ChallengeModel
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apolloClient: ApolloClient,
    private val apiInterface: ApiInterface
) : BaseRepository() {

    suspend fun callUnreadNotificationCount(): ResponseHandler<ApolloResponse<UnreadNotificationCountQuery.Data>?>{
        return graphQlApiCall {
            apolloClient.query(UnreadNotificationCountQuery()).execute()
        }
    }

    suspend fun callCreateChallenge(
        request: RequestBody
    ): ResponseHandler<ResponseData<ChallengeModel>?> {
        return makeAPICall { apiInterface.callCreateChallenge(request) }
    }

}
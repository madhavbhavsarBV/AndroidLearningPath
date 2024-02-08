package com.base.hilt.domain.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.ChallengeDetailQuery
import com.base.hilt.ChallengeListQuery
import com.base.hilt.ChallengeListingCountQuery
import com.base.hilt.UnreadNotificationCountQuery
import com.base.hilt.UserDataQuery
import com.base.hilt.base.BaseRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ChallengeListInput
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : BaseRepository() {


    suspend fun challengeListApi(input: ChallengeListInput): ResponseHandler<ApolloResponse<ChallengeListQuery.Data>> {
        return graphQlApiCall {
            apolloClient.query(ChallengeListQuery(input)).execute()
        }
    }

    suspend fun challengeListingCount(): ResponseHandler<ApolloResponse<ChallengeListingCountQuery.Data>> {
        return graphQlApiCall {
            apolloClient.query(ChallengeListingCountQuery()).execute()
        }
    }

    suspend fun userData(): ResponseHandler<ApolloResponse<UserDataQuery.Data>> {
        return graphQlApiCall {
            apolloClient.query(UserDataQuery()).execute()
        }
    }

    suspend fun unreadNotificationCount(): ResponseHandler<ApolloResponse<UnreadNotificationCountQuery.Data>> {
        return graphQlApiCall {
            apolloClient.query(UnreadNotificationCountQuery()).execute()
        }
    }

    suspend fun challengeDetailApi(uuid:String): ResponseHandler<ApolloResponse<ChallengeDetailQuery.Data>> {
        return graphQlApiCall {
            apolloClient.query(ChallengeDetailQuery(uuid)).execute()
        }
    }
}
package com.base.hilt.domain.repository

import com.base.hilt.base.BaseRepository
import com.base.hilt.network.ApiInterface
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.ui.challenge.model.ChallengeModel
import com.base.hilt.ui.challenge.model.ContactsModel
import com.base.hilt.ui.challenge.model.ContactsRequest
import okhttp3.RequestBody
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiInterface: ApiInterface
) : BaseRepository() {

//    suspend fun callUnreadNotificationCount(): ResponseHandler<ApolloResponse<UnreadNotificationCountQuery.Data>?>{
//        return graphQlApiCall {
//            apolloClient.query(UnreadNotificationCountQuery()).execute()
//        }
//    }

    suspend fun createChallengeApi(
        req: RequestBody
    ): ResponseHandler<ResponseData<ChallengeModel>?> {
        return makeAPICall { apiInterface.createChallengeApi(req) }
    }

    suspend fun contactsApi(
        req: ContactsRequest
    ): ResponseHandler<ResponseData<ContactsModel>?> {
        return makeAPICall { apiInterface.contactsApi(req) }
    }

}
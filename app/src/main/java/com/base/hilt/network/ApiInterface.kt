package com.base.hilt.network

import com.base.hilt.domain.model.ChallengeRequestModel
import com.base.hilt.ui.challenge.model.ChallengeModel

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import javax.inject.Singleton

/**
 * Interface used for api
 */
@Singleton
interface ApiInterface {


    @POST(ApiEndPoints.API_PATH + ApiEndPoints.CREATE_CHALLENGE)
    @Multipart
    suspend fun callCreateChallenge(
        @Body request: RequestBody
    ): Response<ResponseData<ChallengeModel>>


}

package com.base.hilt.network

import com.base.hilt.ui.challenge.model.ChallengeModel

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton

/**
 * Interface used for api
 */
@Singleton
interface ApiInterface {


    @POST("/${ApiEndPoints.API_PATH}"+ApiEndPoints.API_VERSION + ApiEndPoints.CREATE_CHALLENGE)
    suspend fun callCreateChallenge(
        @Body requestBody: RequestBody
    ): Response<ResponseData<ChallengeModel>>


}

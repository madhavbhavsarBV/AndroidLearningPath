package com.base.hilt.network

import com.base.hilt.domain.model.ChallengeRequestModel
import com.base.hilt.ui.challenge.model.ChallengeModel
import com.base.hilt.ui.challenge.model.ContactsModel
import com.base.hilt.ui.challenge.model.ContactsRequest

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


    @POST("https://vmeapi.demo.brainvire.dev/api/V1/add-challenge")
    suspend fun createChallengeApi(
        @Body request: RequestBody
    ): Response<ResponseData<ChallengeModel>>


    @POST("https://vmeapi.demo.brainvire.dev/api/V1/registered-contact-sync")
    suspend fun contactsApi(
        @Body request: ContactsRequest
    ): Response<ResponseData<ContactsModel>>


}

package com.base.hilt.ui.signup.repository

import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.LoginMutation
import com.base.hilt.SignupMutation
import com.base.hilt.base.BaseRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.LoginInput
import com.base.hilt.type.SignUpInput
import javax.inject.Inject

class CreateAccountRepository @Inject constructor(val apolloClient: ApolloClient) :
    BaseRepository() {
    suspend fun onSignUpApi(signUpInput: SignUpInput): ResponseHandler<ApolloResponse<SignupMutation.Data>> {
        Log.i("mad2", "onsignupApi: api called")
        return try {
            val response = apolloClient.mutation(SignupMutation(signUpInput)).execute()

            if (response.hasErrors() || response == null) {
                Log.i("mad2", "onsignupApi:f ${response.errors!![0].message}")
                ResponseHandler.OnFailed(
                    0,
                    response.errors!![0].message,
                    response.data!!.signup.meta.message_code
                )
            } else {
                Log.i("mad2", "onsignupApi:s ${response}")
                ResponseHandler.OnSuccessResponse(response)
            }

        } catch (e: Exception) {
            Log.i("mad2", "onsignupApi: exception ")
            //
            ResponseHandler.OnFailed(0, "exception ${e}", "0")
        }
    }

}
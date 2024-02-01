package com.base.hilt.ui.forgotpassword.repository

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.exception.ApolloException
import com.base.hilt.ForgotPasswordMutation
import com.base.hilt.base.BaseRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ForgotPasswordInput
import javax.inject.Inject

class ForgotPasswordRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : BaseRepository() {


    suspend fun onForgotPasswordApi(forgotPasswordInput: ForgotPasswordInput): ResponseHandler<ApolloResponse<ForgotPasswordMutation.Data>> {
        return try {
            val response = apolloClient.mutation(ForgotPasswordMutation(forgotPasswordInput)).execute()

            if (response.hasErrors()){
                ResponseHandler.OnFailed(0, response.errors!![0].message, response.data!!.forgotPassword.meta.message_code)
            }else{
                ResponseHandler.OnSuccessResponse(response)
            }

        } catch (e: ApolloException) {
            ResponseHandler.OnFailed(0,"Exception1 ${e}", "0")
        } catch (e:Exception){
            ResponseHandler.OnFailed(0,"Exception2 ${e}", "0")
        }
    }

}

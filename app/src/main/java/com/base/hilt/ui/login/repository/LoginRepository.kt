package com.base.hilt.ui.login.repository


import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.LoginMutation
import com.base.hilt.base.BaseRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.LoginInput
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : BaseRepository() {


    suspend fun onLoginApi(loginReq: LoginInput): ResponseHandler<ApolloResponse<LoginMutation.Data>> {
        return try {
            val response = apolloClient.mutation(LoginMutation(loginReq)).execute()

            if (response.hasErrors() || response==null){
                ResponseHandler.OnFailed(0, response.errors!![0].message, "0")
            }else{
                ResponseHandler.OnSuccessResponse(response)
            }

        } catch (e: Exception) {
            ResponseHandler.OnFailed(0,"Exception", "0")
        }
    }


}
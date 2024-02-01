package com.base.hilt.ui.login.repository


import android.util.Log
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.base.hilt.ForgotPasswordMutation
import com.base.hilt.LoginMutation
import com.base.hilt.base.BaseRepository
import com.base.hilt.network.ResponseHandler
import com.base.hilt.type.ForgotPasswordInput
import com.base.hilt.type.LoginInput
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val apolloClient: ApolloClient
) : BaseRepository() {


    suspend fun onLoginApi(loginReq: LoginInput): ResponseHandler<ApolloResponse<LoginMutation.Data>> {
        Log.i("mad2", "onLoginApi: api called")
//        return try {
            val response = apolloClient.mutation(LoginMutation(loginReq)).execute()

            if (response.hasErrors() || response== null){
                Log.i("mad2", "onLoginApi:f ${response.errors!![0].message}")
               return ResponseHandler.OnFailed(0, response.errors!![0].message, response.data!!.login.meta.message_code)
            }else{
                Log.i("mad2", "onLoginApi:s ${response}")

               return ResponseHandler.OnSuccessResponse(response)
            }

//        } catch (e:Exception){
//            Log.i("mad2", "onLoginApi: exception ")
//            //
//            ResponseHandler.OnFailed(0,"exception ${e}","0")
//        }
    }


}
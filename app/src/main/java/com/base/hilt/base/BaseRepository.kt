package com.base.hilt.base

import android.accounts.NetworkErrorException
import android.util.Log
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.base.hilt.network.ErrorWrapper
import com.base.hilt.network.GraphQLErrors
import com.base.hilt.network.HttpCommonMethod
import com.base.hilt.network.HttpErrorCode
import com.base.hilt.network.ResponseData
import com.base.hilt.network.ResponseHandler
import com.base.hilt.utils.Constants
import com.base.hilt.utils.Constants.JSON
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import com.fasterxml.jackson.module.kotlin.readValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


open class BaseRepository() {

    suspend fun <T : Operation.Data> graphQlApiCall(call: suspend () -> ApolloResponse<T>): ResponseHandler<ApolloResponse<T>> {
        try {
            val response = call.invoke()
            when {
                response == null -> {
                    return ResponseHandler.OnFailed(
                        code = HttpErrorCode.BAD_RESPONSE.code,
                        message = HttpErrorCode.BAD_RESPONSE.message,
                        messageCode = null,
                    )
                }

                response.hasErrors() -> {

                    val errorModel = HttpCommonMethod.getErrorMessageForGraph(
                        response.errors
                    )

                    //                val error = response.errors?.let { GraphQLErrors(it) }
                    //                Log.i("madmad", "onLoginApi: here2")
                    return ResponseHandler.OnFailed(
                        code = errorModel.first,
                        message = errorModel.second,
                        messageCode = errorModel.third,
                    )
                }

                else -> {
                    return ResponseHandler.OnSuccessResponse(response)
                }
            }

        } catch (e: java.lang.Exception) {
            when (e) {
                is ApolloNetworkException -> {
                    return ResponseHandler.OnFailed(
                        code = HttpErrorCode.NO_CONNECTION.code,
                        message = HttpErrorCode.NO_CONNECTION.message,
                        messageCode = null,
                    )
                }

                is ApolloHttpException -> {
                    return ResponseHandler.OnFailed(
                        code = HttpErrorCode.BAD_RESPONSE.code,
                        message = e.message,
                        messageCode = null,
                    )
                }

                else -> {
                    return ResponseHandler.OnFailed(
                        code = HttpErrorCode.BAD_RESPONSE.code,
                        message = e.message,
                        messageCode = null,
                    )
                }

            }
        }
    }

    suspend fun <T : Operation.Data> graphQlApiCallWithFlow(call: suspend () -> ApolloResponse<T>): Flow<ResponseHandler<ApolloResponse<T>>> {
        return flow {
            emit(ResponseHandler.Loading)
            try {
                val response = call.invoke()

                when {
                    response == null -> {
                        emit(
                            ResponseHandler.OnFailed(
                                code = HttpErrorCode.BAD_RESPONSE.code,
                                message = HttpErrorCode.BAD_RESPONSE.message,
                                messageCode = null,
                                data = null
                            )
                        )
                    }

                    response.hasErrors() -> {
                        val errorModel = HttpCommonMethod.getErrorMessageForGraph(
                            response.errors
                        )
                        emit(
                            ResponseHandler.OnFailed(
                                code = errorModel.first,
                                message = errorModel.second,
                                messageCode = errorModel.third,
                                data = null
                            )
                        )
                    }

                    else -> {
                        emit(ResponseHandler.OnSuccessResponse(response))
                    }

                }

            } catch (e: java.lang.Exception) {
                when (e) {
                    is ApolloNetworkException -> {
                        emit(
                            ResponseHandler.OnFailed(
                                code = HttpErrorCode.NO_CONNECTION.code,
                                message = HttpErrorCode.NO_CONNECTION.message,
                                messageCode = null, data = null
                            )
                        )
                    }

                    is ApolloHttpException -> {
                        emit(
                            ResponseHandler.OnFailed(
                                code = HttpErrorCode.BAD_RESPONSE.code,
                                message = e.message,
                                messageCode = null, data = null
                            )
                        )
                    }

                    else -> {
                        emit(
                            ResponseHandler.OnFailed(
                                code = HttpErrorCode.BAD_RESPONSE.code,
                                message = e.message,
                                messageCode = null, data = null
                            )
                        )
                    }

                }
            }
        }


    }


//suspend fun <T:Any> makeAPICallGraphQL(call: suspend ()->ApolloResponse<Mutation.Data>): ResponseHandler<T>{
//    return try {
//        val response = call
//
//        if(response==null){
//            return ResponseHandler.OnFailed(HttpErrorCode.BAD_RESPONSE.code, HttpErrorCode.BAD_RESPONSE.name,"")
//        }else if(response.hasErrors()){
//            Log.i("madmad", "onLoginApi: here2")
//            return ResponseHandler.OnFailed(0, response.errors!![0].message,"")
//        }else{
//            return ResponseHandler.OnSuccessResponse(response)
//        }
//
//    } catch (e:Exception){
//        Log.i("madmad", "onLoginApi: ${e}")
//        when(e){
//            is ApolloException ->{
//                return (ResponseHandler.OnFailed(
//                    HttpErrorCode.NO_CONNECTION.code,
//                    HttpErrorCode.NO_CONNECTION.name,""))
//            }
//            is ApolloHttpException ->{
//                return (ResponseHandler.OnFailed(HttpErrorCode.BAD_RESPONSE.code,e.message.toString(),""))
//            }
//            else->{
//                Log.i("madmad", "onLoginApi: this is called")
//                null
//                //return (ResponseHandler.OnFailed(HttpErrorCode.BAD_RESPONSE.code,e.message.toString(),""))
//            }
//
//        }
////
//    }
//    }


    //
    suspend fun <T : Any> makeAPICall(call: suspend () -> Response<ResponseData<T>>): ResponseHandler<ResponseData<T>?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = call.invoke()
                when {
                    response.code() in (200..300) -> {
                        return@withContext when (response.body()?.meta?.statusCode) {
                            400 -> {

                                ResponseHandler.OnFailed(
                                    response.body()?.meta?.statusCode!!,
                                    response.body()?.meta?.message!!,
                                    "0"
                                )
                            }

                            401 -> {
                                ResponseHandler.OnFailed(
                                    HttpErrorCode.UNAUTHORIZED.code,
                                    response.body()?.meta?.message!!,
                                    response.body()?.meta?.statusCode?.toString()
                                        ?: Constants.EMPTY
                                )
                            }

                            else -> ResponseHandler.OnSuccessResponse(response.body())
                        }
                    }

                    response.code() == 401 -> {
                        return@withContext parseUnAuthorizeResponse(response.errorBody())
                    }

                    response.code() == 422 -> {
                        return@withContext parseServerSideErrorResponse(response.errorBody())
                    }

                    response.code() == 500 -> {
                        return@withContext ResponseHandler.OnFailed(
                            HttpErrorCode.NOT_DEFINED.code,
                            "",
                            response.body()?.meta?.messageCode.toString()
                        )
                    }

                    else -> {
                        return@withContext parseUnKnownStatusCodeResponse(response.errorBody())
                    }
                }
            } catch (e: Exception) {
                return@withContext when (e) {
                    is UnknownHostException, is ConnectionShutdownException -> {
                        ResponseHandler.OnFailed(HttpErrorCode.NO_CONNECTION.code, "", "")
                    }

                    is SocketTimeoutException, is IOException, is NetworkErrorException -> {
                        ResponseHandler.OnFailed(HttpErrorCode.NOT_DEFINED.code, "", "")
                    }

                    else -> {
                        ResponseHandler.OnFailed(HttpErrorCode.NOT_DEFINED.code, "", "")
                    }
                }
            }
        }
    }

    //
//    /**
//     * Response parsing for 401 status code
//     **/
    private fun parseUnAuthorizeResponse(response: ResponseBody?): ResponseHandler.OnFailed<Nothing> {
        val message: String
        val bodyString = response?.string() ?: Constants.EMPTY
        val responseWrapper: ErrorWrapper = JSON.readValue(bodyString)
        message = if (responseWrapper.meta?.statusCode == 401) {
            if (responseWrapper.errors != null) {
                HttpCommonMethod.getErrorMessage(responseWrapper.errors)
            } else {
                responseWrapper.meta.message.toString()
            }
        } else {
            responseWrapper.meta?.message.toString()
        }
        return ResponseHandler.OnFailed(
            data = null,
            code = HttpErrorCode.UNAUTHORIZED.code,
            message = message,
            messageCode = responseWrapper.meta?.messageCode.toString()
        )
    }

    /**
     * Response parsing for 422 status code
     * */
    private fun parseServerSideErrorResponse(response: ResponseBody?): ResponseHandler.OnFailed<Nothing> {
        val message: String
        val bodyString = response?.string()
        val responseWrapper: ErrorWrapper = JSON.readValue(bodyString!!)

        message = if (responseWrapper.meta?.statusCode == 422) {
            if (responseWrapper.errors != null) {
                HttpCommonMethod.getErrorMessage(responseWrapper.errors)
            } else {
                responseWrapper.meta.message.toString()
            }
        } else {
            responseWrapper.meta?.message.toString()
        }
        return ResponseHandler.OnFailed(
            HttpErrorCode.EMPTY_RESPONSE.code,
            message,
            responseWrapper.meta?.messageCode.toString()
        )
    }

    /**
     * Response parsing for unknown status code
     * */
    private fun parseUnKnownStatusCodeResponse(response: ResponseBody?): ResponseHandler.OnFailed<Nothing> {
        val bodyString = response?.string()
        val responseWrapper: ErrorWrapper = JSON.readValue(bodyString!!)
        val message = if (responseWrapper.meta?.statusCode == 422) {
            if (responseWrapper.errors != null) {
                HttpCommonMethod.getErrorMessage(responseWrapper.errors)
            } else {
                responseWrapper.meta.message.toString()
            }
        } else {
            responseWrapper.meta?.message.toString()
        }
        return if (message.isEmpty()) {
            ResponseHandler.OnFailed(
                HttpErrorCode.EMPTY_RESPONSE.code,
                message,
                responseWrapper.meta?.messageCode.toString()
            )
        } else {
            ResponseHandler.OnFailed(
                HttpErrorCode.NOT_DEFINED.code,
                message,
                responseWrapper.meta?.messageCode.toString()
            )
        }
    }
}

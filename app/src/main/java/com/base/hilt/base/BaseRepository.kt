package com.base.hilt.base

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Query


open class BaseRepository() {





//
//    suspend fun <T : Any> makeAPICall(call: suspend () -> Response<ResponseData<T>>): ResponseHandler<ResponseData<T>?> {
//        return withContext(Dispatchers.IO) {
//            try {
//                val response = call.invoke()
//                when {
//                    response.code() in (200..300) -> {
//                        return@withContext when (response.body()?.meta?.status_code) {
//                            400 -> {
//
//                                ResponseHandler.OnFailed(
//                                    response.body()?.meta?.status_code!!,
//                                    response.body()?.meta?.message!!,
//                                    "0"
//                                )
//                            }
//                            401 -> {
//                                ResponseHandler.OnFailed(
//                                    HttpErrorCode.UNAUTHORIZED.code,
//                                    response.body()?.meta?.message!!,
//                                    response.body()?.meta?.status_code?.toString() ?: Constants.EMPTY
//                                )
//                            }
//                            else -> ResponseHandler.OnSuccessResponse(response.body())
//                        }
//                    }
//                    response.code() == 401 -> {
//                        return@withContext parseUnAuthorizeResponse(response.errorBody())
//                    }
//                    response.code() == 422 -> {
//                        return@withContext parseServerSideErrorResponse(response.errorBody())
//                    }
//                    response.code() == 500 -> {
//                        return@withContext ResponseHandler.OnFailed(
//                            HttpErrorCode.NOT_DEFINED.code,
//                            "",
//                            response.body()?.meta?.message_code.toString()
//                        )
//                    }
//                    else -> {
//                        return@withContext parseUnKnownStatusCodeResponse(response.errorBody())
//                    }
//                }
//            } catch (e: Exception) {
//                DebugLog.print(e)
//                return@withContext when (e) {
//                    is UnknownHostException, is ConnectionShutdownException -> {
//                        ResponseHandler.OnFailed(HttpErrorCode.NO_CONNECTION.code, "", "")
//                    }
//                    is SocketTimeoutException, is IOException, is NetworkErrorException -> {
//                        ResponseHandler.OnFailed(HttpErrorCode.NOT_DEFINED.code, "", "")
//                    }
//                    else -> {
//                        ResponseHandler.OnFailed(HttpErrorCode.NOT_DEFINED.code, "", "")
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * Response parsing for 401 status code
//     **/
//    private fun parseUnAuthorizeResponse(response: ResponseBody?): ResponseHandler.OnFailed {
//        val message: String
//        val bodyString = response?.string() ?: Constants.EMPTY
//        val responseWrapper: ErrorWrapper = JSON.readValue(bodyString)
//        message = if (responseWrapper.meta?.status_code == 401) {
//            if (responseWrapper.errors != null) {
//                HttpCommonMethod.getErrorMessage(responseWrapper.errors)
//            } else {
//                responseWrapper.meta.message.toString()
//            }
//        } else {
//            responseWrapper.meta?.message.toString()
//        }
//        return ResponseHandler.OnFailed(
//            HttpErrorCode.UNAUTHORIZED.code,
//            message,
//            responseWrapper.meta?.message_code.toString()
//        )
//    }
//
//    /**
//     * Response parsing for 422 status code
//     * */
//    private fun parseServerSideErrorResponse(response: ResponseBody?): ResponseHandler.OnFailed {
//        val message: String
//        val bodyString = response?.string()
//        val responseWrapper: ErrorWrapper = JSON.readValue(bodyString!!)
//
//        message = if (responseWrapper.meta?.status_code == 422) {
//            if (responseWrapper.errors != null) {
//                HttpCommonMethod.getErrorMessage(responseWrapper.errors)
//            } else {
//                responseWrapper.meta.message.toString()
//            }
//        } else {
//            responseWrapper.meta?.message.toString()
//        }
//        return ResponseHandler.OnFailed(
//            HttpErrorCode.EMPTY_RESPONSE.code,
//            message,
//            responseWrapper.meta?.message_code.toString()
//        )
//    }
//
//    /**
//     * Response parsing for unknown status code
//     * */
//    private fun parseUnKnownStatusCodeResponse(response: ResponseBody?): ResponseHandler.OnFailed {
//        val bodyString = response?.string()
//        val responseWrapper: ErrorWrapper = JSON.readValue(bodyString!!)
//        val message = if (responseWrapper.meta?.status_code == 422) {
//            if (responseWrapper.errors != null) {
//                HttpCommonMethod.getErrorMessage(responseWrapper.errors)
//            } else {
//                responseWrapper.meta.message.toString()
//            }
//        } else {
//            responseWrapper.meta?.message.toString()
//        }
//        return if (message.isEmpty()) {
//            ResponseHandler.OnFailed(
//                HttpErrorCode.EMPTY_RESPONSE.code,
//                message,
//                responseWrapper.meta?.message_code.toString()
//            )
//        } else {
//            ResponseHandler.OnFailed(
//                HttpErrorCode.NOT_DEFINED.code,
//                message,
//                responseWrapper.meta?.message_code.toString()
//            )
//        }
//    }
}

//package com.base.hilt.base
//
//
//import com.apollographql.apollo3.ApolloCall
//import com.apollographql.apollo3.ApolloPrefetch
//import com.apollographql.apollo3.ApolloQueryWatcher
//import com.apollographql.apollo3.ApolloSubscriptionCall
//import com.apollographql.apollo3.api.Response
//import com.apollographql.apollo3.exception.ApolloException
//import kotlinx.coroutines.*
//import kotlinx.coroutines.channels.Channel
//import kotlinx.coroutines.flow.flow
//
//
///**
// * Common class for API calling
// */
//
//open class BaseGraphRepository {
//
//    /**
//     * This is the Base suspended method which is used for making the call of an Api and
//     * Manage the Response with response code to display specific response message or code.
//     * @param call ApiInterface method defination to make a call and get response from generic Area.
//     */
//    suspend fun <T> ApolloCall<T>.execute() = suspendCoroutine<Response<T>> { cont ->
//        enqueue(object : ApolloCall.Callback<T>() {
//            override fun onResponse(response: Response<T>) {
//                cont.resume(response)
//            }
//
//            override fun onFailure(e: ApolloException) {
//                cont.resumeWithException(e)
//            }
//        })
//    }
//
//    fun <T> ApolloCall<T>.toFlow() = callbackFlow {
//        clone().enqueue(
//            object : ApolloCall.Callback<T>() {
//                override fun onResponse(response: Response<T>) {
//                    runCatching {
//                        offer(response)
//                    }
//                }
//
//                override fun onFailure(e: ApolloException) {
//                    close(e)
//                }
//
//                override fun onStatusEvent(event: ApolloCall.StatusEvent) {
//                    if (event == ApolloCall.StatusEvent.COMPLETED) {
//                        close()
//                    }
//                }
//            }
//        )
//        awaitClose { this@toFlow.cancel() }
//    }
//
//
//
//
//}

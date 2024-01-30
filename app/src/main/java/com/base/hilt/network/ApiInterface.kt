package com.base.hilt.network

import androidx.lifecycle.MutableLiveData
import com.base.hilt.ConfigFiles
import com.base.hilt.ui.home.repository.HomeScreenVendorsListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

/**
 * Interface used for api
 */
@Singleton
interface ApiInterface {
//    @GET(ConfigFiles.API_VERSION + "find-vendors")
//    suspend fun callHomeScreenApiGetVendors(
//        @Query("vendor_type") vendorType: String,
//        @Query("lat") lat: Double,
//        @Query("long") long: Double,
//    ): Response<ResponseData<HomeScreenVendorsListResponse>>

//
//    suspend fun onloginApi(loginReq: LoginInput): Response<ResponseData<LoginResponse>>


}

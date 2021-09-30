package com.apolis.mvvm_demo1.api

import com.apolis.twowaydatabindingpractice.data.request.RegisterRequestData
import com.apolis.twowaydatabindingpractice.data.response.RegisterResponseData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {

    @Headers("Content-type: application/json")


    @POST("auth/register")
    fun register(
        @Body registerData: RegisterRequestData
    ): Call<RegisterResponseData>

}
package com.example.mypay.data

import com.example.mypay.data.payments.Payments
import retrofit2.Call
import retrofit2.http.*

interface ThePayment {

    @Headers("app-key: 12345","v: 1")
    @POST("/api-test/login")
     fun getToken(
        @Body bodyToken: BodyToken
    ): Call<UserToken>

    @Headers("app-key: 12345","v: 1")
     @GET("/api-test/payments")
     fun getPayments(
         @Header("token") token:String
     ): Call<Payments>

}
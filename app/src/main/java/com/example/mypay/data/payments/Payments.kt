package com.example.mypay.data.payments


import com.google.gson.annotations.SerializedName

data class Payments(
    @SerializedName("response")
    val response: List<Response>,
    @SerializedName("success")
    val success: String
)
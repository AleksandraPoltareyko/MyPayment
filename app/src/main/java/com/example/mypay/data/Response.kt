package com.example.mypay.data


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("token")
    val token: String
)
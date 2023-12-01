package com.example.mypay.data


import com.google.gson.annotations.SerializedName

data class UserToken(
    @SerializedName("response")
    val response: Response,
    @SerializedName("success")
    val success: String
)
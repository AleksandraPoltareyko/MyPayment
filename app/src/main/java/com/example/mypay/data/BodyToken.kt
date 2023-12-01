package com.example.mypay.data


import com.google.gson.annotations.SerializedName

data class BodyToken(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String



)
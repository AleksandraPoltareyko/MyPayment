package com.example.mypay.data.payments


import com.google.gson.annotations.SerializedName

data class Response(
    @SerializedName("amount")
    val amount: String? = "",
    @SerializedName("created")
    val created: Int? = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = ""
)
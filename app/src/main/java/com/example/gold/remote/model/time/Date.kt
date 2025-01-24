package com.example.gold.remote.model.time

import com.google.gson.annotations.SerializedName

data class Date(
    @SerializedName("F") val month: String,
    @SerializedName("Y") val year: String,
    @SerializedName("l") val day: String,
    @SerializedName("j") val dayOfMonth: String
)
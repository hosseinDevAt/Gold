package com.example.gold.remote.gold

import com.example.gold.remote.model.gold.CurrenciesModel
import retrofit2.Call
import retrofit2.http.GET

interface GoldApiService {

    @GET("currencies")
    fun getCurrencies():Call<CurrenciesModel>

}
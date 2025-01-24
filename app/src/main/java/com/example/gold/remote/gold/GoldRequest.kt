package com.example.gold.remote.gold

import com.example.gold.remote.model.gold.CurrenciesModel

interface GoldRequest {

    fun onSuccess(data: CurrenciesModel)

    fun onNotSuccess(message: String)

    fun onError(message: String)
}
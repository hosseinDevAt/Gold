package com.example.gold.remote.time

import com.example.gold.remote.model.time.DateModel

interface TimeRequest {

    fun onSuccess(date: DateModel)

    fun onNotSuccess(message: String)

    fun onError(message: String)
}
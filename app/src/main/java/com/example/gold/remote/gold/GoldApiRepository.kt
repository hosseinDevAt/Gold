package com.example.gold.remote.gold

import com.example.gold.remote.MainRetrofitService
import com.example.gold.remote.model.gold.CurrenciesModel
import com.example.gold.remote.model.time.DateModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoldApiRepository private constructor() {


    companion object {

        private var apiRepository: GoldApiRepository? = null


        val instance: GoldApiRepository
            get() {
                if (apiRepository == null) apiRepository = GoldApiRepository()
                return apiRepository!!
            }

    }

    fun getCurrencies(
        request: GoldRequest
    ) {

        MainRetrofitService.goldApiService.getCurrencies().enqueue(

            object : Callback<CurrenciesModel> {
                override fun onResponse(
                    call: Call<CurrenciesModel>,
                    response: Response<CurrenciesModel>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body() as CurrenciesModel
                        request.onSuccess(data)
                    } else
                        request.onNotSuccess("Not Success")
                }

                override fun onFailure(call: Call<CurrenciesModel>, t: Throwable) {
                    request.onError(t.message.toString())
                }
            }

        )

    }


}
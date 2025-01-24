package com.example.gold.remote.time

import com.example.gold.remote.MainRetrofitService
import com.example.gold.remote.model.time.DateModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimeApiRepository private constructor() {


    companion object {

        private var apiRepository: TimeApiRepository? = null


        val instance: TimeApiRepository
            get() {
                if (apiRepository == null) apiRepository = TimeApiRepository()
                return apiRepository!!
            }

    }

    fun getTimeNow(
        request: TimeRequest
    ) {

        MainRetrofitService.timeApiService.getTime(true).enqueue(
            object : Callback<DateModel> {

                override fun onResponse(call: Call<DateModel>, response: Response<DateModel>) {

                    val data = response.body() as DateModel
                    if (response.isSuccessful)
                        request.onSuccess(data)
                    else
                        request.onNotSuccess(data.message)
                }

                override fun onFailure(call: Call<DateModel>, t: Throwable) {
                    request.onError(t.message.toString())
                }

            }
        )

    }


}
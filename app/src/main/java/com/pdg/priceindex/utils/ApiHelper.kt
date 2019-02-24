package com.pdg.priceindex.utils

import com.pdg.priceindex.model.History
import com.pdg.priceindex.model.TickerValue
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiHelper {

    @GET("ticker/BTCEUR")
    fun getTickerValues() : Call<TickerValue>

    @GET("history/BTCEUR?period=monthly&?format=json")
    fun getHistoricalData() : Call<List<History>>

    companion object Factory {
        fun create(): ApiHelper {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL+Constants.API_URL)
                .build()

            return retrofit.create(ApiHelper::class.java)
        }
    }
}
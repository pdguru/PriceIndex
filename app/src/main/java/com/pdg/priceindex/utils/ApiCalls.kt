package com.pdg.priceindex.utils

import retrofit2.http.GET

interface ApiCalls {

    @GET("ticker/BTCEUR")
    fun getTickerValues
}
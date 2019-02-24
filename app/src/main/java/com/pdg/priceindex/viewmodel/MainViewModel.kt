package com.pdg.priceindex.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.pdg.priceindex.model.History
import com.pdg.priceindex.model.TickerValue
import com.pdg.priceindex.utils.ApiHelper
import com.pdg.priceindex.utils.Constants
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

class MainViewModel : ViewModel() {

    var tikrValues: MutableLiveData<TickerValue>
    var historyValues: MutableLiveData<List<History>>

    init {
        tikrValues = MutableLiveData()
        historyValues = MutableLiveData()

        //for initial run
        getTikrValues()
        getHistoryValues()

        //for subsequent runs
        fetchData()
    }

    private fun fetchData() {
        var tikrDispose = Observable.interval(0,5,TimeUnit.SECONDS).subscribe({
            getTikrValues()
        }, Throwable::printStackTrace)

        var histDispose = Observable.interval(0,5,TimeUnit.MINUTES).subscribe({
            getHistoryValues()
        }, Throwable::printStackTrace)
    }

    private fun getHistoryValues() {

        val call = ApiHelper.create().getHistoricalData()

        call.enqueue(object : Callback<List<History>> {
            override fun onResponse(call: Call<List<History>>, response: Response<List<History>>) {
                if (response.isSuccessful) {
                    Log.i(Constants.TAG, "➡️ ${response.body()}")
                    historyValues.value = response.body()
                } else {
                    Log.e(Constants.TAG, "❌(HISTORY) SOMETHING WENT WRONG. ${response.code()}")
                    historyValues.value = null
                }
            }

            override fun onFailure(call: Call<List<History>>, t: Throwable) {
                Log.e(Constants.TAG, "❌ ${t.cause}")
                t.printStackTrace()
            }
        })

    }

    private fun getTikrValues() {

        val call = ApiHelper.create().getTickerValues()

        call.enqueue(object : Callback<TickerValue> {
            override fun onResponse(call: Call<TickerValue>, response: Response<TickerValue>) {
                if (response.isSuccessful) {
                    Log.i(Constants.TAG, "➡️ ${response.body()}")
                    tikrValues.value = response.body()
                } else {
                    Log.e(Constants.TAG, "❌(TICKER) SOMETHING WENT WRONG. ${response.code()}")
                    tikrValues.value = null
                }
            }

            override fun onFailure(call: Call<TickerValue>, t: Throwable) {
                Log.e(Constants.TAG, "❌ ${t.cause}")
                t.printStackTrace()
            }
        })
    }
}
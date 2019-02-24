package com.pdg.priceindex.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import android.widget.Toast
import com.pdg.priceindex.model.History
import com.pdg.priceindex.model.TickerValue
import com.pdg.priceindex.utils.ApiHelper
import com.pdg.priceindex.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    lateinit var tikrValues: MutableLiveData<TickerValue>
    lateinit var historyValues : MutableLiveData<List<History>>

    init {
        getTikrValues()
        getHistoryValues()
    }

    private fun getHistoryValues() {
        historyValues = MutableLiveData()

        val call = ApiHelper.create().getHistoricalData()

        call.enqueue(object : Callback<List<History>>{
            override fun onResponse(call: Call<List<History>>, response: Response<List<History>>) {
                if(response.isSuccessful){
                    Log.i(Constants.TAG, "➡️ ${response.body()}")
                    historyValues.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<History>>, t: Throwable) {
                Log.e(Constants.TAG,"❌ ${t.cause}")
                t.printStackTrace()
            }
        })
    }

    private fun getTikrValues(){
        tikrValues = MutableLiveData()

        val call = ApiHelper.create().getTickerValues()

        call.enqueue(object : Callback<TickerValue>{
            override fun onResponse(call: Call<TickerValue>, response: Response<TickerValue>) {
                if(response.isSuccessful){
                    Log.i(Constants.TAG, "➡️ ${response.body()}")
                    tikrValues.value = response.body()
                }
            }

            override fun onFailure(call: Call<TickerValue>, t: Throwable) {
                Log.e(Constants.TAG,"❌ ${t.cause}")
                t.printStackTrace()
            }
        })
    }
}
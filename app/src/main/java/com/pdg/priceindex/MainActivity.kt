package com.pdg.priceindex

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.anychart.data.View
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.pdg.priceindex.R.id.lastPriceBTC
import com.pdg.priceindex.model.History
import com.pdg.priceindex.model.TickerValue
import com.pdg.priceindex.utils.Constants
import com.pdg.priceindex.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val data = ArrayList<DataEntry>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        //observe LiveData from viewModel for last price and ask price
        val mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        //setup data calls
        mainViewModel.tikrValues.observe(this, Observer<TickerValue> { trk->
            Log.i(Constants.TAG,"ℹ️ $trk")
            lastPriceBTC.text = "${trk?.last}€"
            askPriceBTC.text = "${trk?.ask}€"
            timeStampTV.text = "${trk?.display_timestamp}"
        })

        mainViewModel.historyValues.observe(this, Observer<List<History>> { hist->
            data.clear()
            for(h in hist!!.iterator()){
                data.add(ValueDataEntry(h.time,h.average))
            }
            //show historical data on the chart
            setupChart()
        })
    }

    private fun setupChart() {
        val line = AnyChart.line()
        line.animation(true)
        line.crosshair().enabled(true)

        val set = Set.instantiate()
        set.data(data)
        var series1Mapping = set.mapAs("{ x: 'x', value: 'value' }")

        val series1 = line.line(series1Mapping)
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)

        progressBar.visibility = android.view.View.GONE
        val anyChartView = findViewById<AnyChartView>(R.id.chart_view)
        anyChartView.setChart(line)
    }
}


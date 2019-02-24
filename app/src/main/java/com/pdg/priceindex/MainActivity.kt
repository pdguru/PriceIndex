package com.pdg.priceindex

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setup Viewmodel connector
        //setup data calls
    }

    override fun onStart() {
        super.onStart()

        //observe LiveData from viewModel for last price and ask price

        //get and update historical data on the chart
        setupChart()
    }

    private fun setupChart() {
        val line = AnyChart.line()
        line.animation(true)
        line.crosshair().enabled(true)

        val data = ArrayList<DataEntry>()
        data.add(ValueDataEntry("John", 10000))
        data.add(ValueDataEntry("Jake", 12000))
        data.add(ValueDataEntry("Peter", 18000))

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

        val anyChartView = findViewById<AnyChartView>(R.id.chart_view)
        anyChartView.setChart(line)
    }
}


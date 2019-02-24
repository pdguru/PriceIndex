package com.pdg.priceindex.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TickerValue(
    var last : Double,
    var ask : Double,
    var timestamp : Long,
    var display_timestamp : String
): Parcelable
package com.pdg.priceindex.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class History (
    var average : Double,
    var time : String
): Parcelable
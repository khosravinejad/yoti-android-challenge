package com.yoti.android.cryptocurrencychallenge.data.source.remote.response


import com.google.gson.annotations.SerializedName
import com.yoti.android.cryptocurrencychallenge.data.source.remote.model.MarketRemote

data class MarketsApiData(
    @SerializedName("data")
        val marketData: List<MarketRemote>,
    @SerializedName("timestamp")
        val timestamp: Long?
)
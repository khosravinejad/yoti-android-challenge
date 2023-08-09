package com.yoti.android.cryptocurrencychallenge.data.source.remote.response


import com.google.gson.annotations.SerializedName
import com.yoti.android.cryptocurrencychallenge.data.source.remote.model.AssetRemote

data class AssetsApiData(
    @SerializedName("data")
        val assetData: List<AssetRemote>,
    @SerializedName("timestamp")
        val timestamp: Long?
)
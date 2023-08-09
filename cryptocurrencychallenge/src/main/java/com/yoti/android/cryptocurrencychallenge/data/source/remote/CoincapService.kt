package com.yoti.android.cryptocurrencychallenge.data.source.remote

import com.yoti.android.cryptocurrencychallenge.data.source.remote.response.AssetsApiData
import com.yoti.android.cryptocurrencychallenge.data.source.remote.response.MarketsApiData
import retrofit2.http.GET
import retrofit2.http.Query

interface CoincapService {

    companion object {
        const val CAPCOIN_ENDPOINT_HOST = "https://api.coincap.io/"
    }

    @GET("/v2/assets")
    suspend fun getAssets(): AssetsApiData

    @GET("/v2/markets")
    suspend fun getMarkets(@Query("baseId") id: String): MarketsApiData
}
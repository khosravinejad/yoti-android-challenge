package com.yoti.android.cryptocurrencychallenge.data.source.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markets")
data class MarketCache(
    @PrimaryKey val id: String,
    val baseId: String?,
    val baseSymbol: String?,
    val exchangeId: String?,
    val percentExchangeVolume: String?,
    val priceQuote: String?,
    val priceUsd: String?,
    val quoteId: String?,
    val quoteSymbol: String?,
    val rank: String?,
    val tradesCount24Hr: String?,
    val updated: Long?,
    val volumeUsd24Hr: String?,
    val insertTimestamp: Long
)
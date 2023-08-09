package com.yoti.android.cryptocurrencychallenge.utils

import com.yoti.android.cryptocurrencychallenge.data.source.local.model.AssetCache
import com.yoti.android.cryptocurrencychallenge.data.source.local.model.MarketCache
import com.yoti.android.cryptocurrencychallenge.data.source.remote.model.AssetRemote
import com.yoti.android.cryptocurrencychallenge.data.source.remote.model.MarketRemote
import com.yoti.android.cryptocurrencychallenge.data.source.remote.response.AssetsApiData
import com.yoti.android.cryptocurrencychallenge.data.source.remote.response.MarketsApiData
import com.yoti.android.cryptocurrencychallenge.domain.model.AssetDomainModel
import com.yoti.android.cryptocurrencychallenge.domain.model.MarketDomainModel
import com.yoti.android.cryptocurrencychallenge.presentation.model.AssetUiItem

object FakeData {

    const val currentMockTimeStamp = 1630456800000

    fun fakeAssetUiItem(itemsCount: Int = 10): List<AssetUiItem> {
        val list = mutableListOf<AssetUiItem>()
        if (itemsCount > 0) {
            for (i in 1..itemsCount) {
                list.add(
                    AssetUiItem(
                        id = "id$i",
                        name = "name$i",
                        price = "price$i",
                        symbol = "SYMBOL$i"
                    )
                )
            }
        }
        return list
    }

    fun fakeAssetDomainModel(itemsCount: Int = 10): List<AssetDomainModel> {
        val list = mutableListOf<AssetDomainModel>()
        if (itemsCount > 0) {
            for (i in 1..itemsCount) {
                list.add(
                    AssetDomainModel(
                        id = "id$i",
                        changePercent24Hr = "$i",
                        explorer = "",
                        marketCapUsd = "",
                        maxSupply = "",
                        name = "name$i",
                        priceUsd = "",
                        rank = "$i",
                        supply = "",
                        symbol = "SYMBOL$i",
                        volumeUsd24Hr = "$i",
                        VolumeWeightedAveragePrice24Hr = ""
                    )
                )
            }
        }
        return list
    }

    fun fakeAssetCache(itemsCount: Int = 10): List<AssetCache> {
        val list = mutableListOf<AssetCache>()
        if (itemsCount > 0) {
            for (i in 1..itemsCount) {
                list.add(
                    AssetCache(
                        id = "id$i",
                        changePercent24Hr = "$i",
                        explorer = "",
                        marketCapUsd = "",
                        maxSupply = "",
                        name = "name$i",
                        priceUsd = "",
                        rank = "$i",
                        supply = "",
                        symbol = "SYMBOL$i",
                        volumeUsd24Hr = "$i",
                        vwap24Hr = "",
                        insertTimestamp = currentMockTimeStamp
                    )
                )
            }
        }
        return list
    }

    fun fakeAssetRemoteModel(itemsCount: Int = 10): List<AssetRemote> {
        val list = mutableListOf<AssetRemote>()
        if (itemsCount > 0) {
            for (i in 1..itemsCount) {
                list.add(
                    AssetRemote(
                        id = "id$i",
                        changePercent24Hr = "$i",
                        explorer = "",
                        marketCapUsd = "",
                        maxSupply = "",
                        name = "name$i",
                        priceUsd = "",
                        rank = "$i",
                        supply = "",
                        symbol = "SYMBOL$i",
                        volumeUsd24Hr = "$i",
                        vwap24Hr = ""
                    )
                )
            }
        }
        return list
    }

    fun fakeAssetRemoteResponse(itemsCount: Int = 10): AssetsApiData {
        return AssetsApiData(fakeAssetRemoteModel(itemsCount), 11111111)
    }

    fun fakeMarketDomainModel(itemsCount: Int = 10): List<MarketDomainModel> {
        val list = mutableListOf<MarketDomainModel>()
        if (itemsCount > 0) {
            for (i in 1..itemsCount) {
                list.add(
                    MarketDomainModel(
                        baseId = "baseId$i",
                        baseSymbol = "baseSymbol$i",
                        exchangeId = "exchangeId$i",
                        percentExchangeVolume = "$i",
                        priceQuote = "priceQuote$i",
                        priceUsd = "$i",
                        quoteId = "quoteId$i",
                        quoteSymbol = "quoteSymbol$i",
                        rank = "$i",
                        tradesCount24Hr = "$i",
                        updated = currentMockTimeStamp,
                        volumeUsd24Hr = "$i"
                    )
                )
            }
        }
        return list
    }

    fun fakeMarketCache(itemsCount: Int = 10): List<MarketCache> {
        val list = mutableListOf<MarketCache>()
        if (itemsCount > 0) {
            for (i in 1..itemsCount) {
                list.add(
                    MarketCache(
                        id = "$i",
                        baseId = "baseId$i",
                        baseSymbol = "baseSymbol$i",
                        exchangeId = "exchangeId$i",
                        percentExchangeVolume = "$i",
                        priceQuote = "priceQuote$i",
                        priceUsd = "$i",
                        quoteId = "quoteId$i",
                        quoteSymbol = "quoteSymbol$i",
                        rank = "$i",
                        tradesCount24Hr = "$i",
                        updated = currentMockTimeStamp,
                        volumeUsd24Hr = "$i",
                        insertTimestamp = currentMockTimeStamp
                    )
                )
            }
        }
        return list
    }

    fun fakeMarketRemoteModel(itemsCount: Int = 10): List<MarketRemote> {
        val list = mutableListOf<MarketRemote>()
        if (itemsCount > 0) {
            for (i in 1..itemsCount) {
                list.add(
                    MarketRemote(
                        baseId = "baseId$i",
                        baseSymbol = "baseSymbol$i",
                        exchangeId = "exchangeId$i",
                        percentExchangeVolume = "$i",
                        priceQuote = "priceQuote$i",
                        priceUsd = "$i",
                        quoteId = "quoteId$i",
                        quoteSymbol = "quoteSymbol$i",
                        rank = "$i",
                        tradesCount24Hr = "$i",
                        updated = currentMockTimeStamp,
                        volumeUsd24Hr = "$i"
                    )
                )
            }
        }
        return list
    }

    fun fakeMarketRemoteResponse(itemsCount: Int = 10): MarketsApiData {
        return MarketsApiData(fakeMarketRemoteModel(itemsCount), 11111111)
    }
}
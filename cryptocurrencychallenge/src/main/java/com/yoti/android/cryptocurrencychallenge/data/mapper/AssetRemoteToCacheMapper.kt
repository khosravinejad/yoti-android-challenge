package com.yoti.android.cryptocurrencychallenge.data.mapper

import com.yoti.android.cryptocurrencychallenge.data.source.local.model.AssetCache
import com.yoti.android.cryptocurrencychallenge.data.source.remote.model.AssetRemote
import com.yoti.android.cryptocurrencychallenge.data.utils.SystemTimestampGenerator
import com.yoti.android.cryptocurrencychallenge.utils.Mapper

class AssetRemoteToCacheMapper(private val timestampGenerator: SystemTimestampGenerator) :
    Mapper<AssetRemote, AssetCache>() {
    override fun mapTo(input: AssetRemote): AssetCache {
        return AssetCache(
            id = input.id,
            changePercent24Hr = input.changePercent24Hr,
            explorer = input.explorer,
            marketCapUsd = input.marketCapUsd,
            maxSupply = input.maxSupply,
            name = input.name,
            priceUsd = input.priceUsd,
            rank = input.rank,
            supply = input.supply,
            symbol = input.symbol,
            volumeUsd24Hr = input.volumeUsd24Hr,
            vwap24Hr = input.vwap24Hr,
            insertTimestamp = timestampGenerator.generateTimestamp()
        )
    }
}
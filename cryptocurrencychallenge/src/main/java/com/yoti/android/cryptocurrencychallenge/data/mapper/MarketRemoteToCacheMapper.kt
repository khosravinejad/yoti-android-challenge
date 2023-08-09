package com.yoti.android.cryptocurrencychallenge.data.mapper

import com.yoti.android.cryptocurrencychallenge.data.source.local.model.MarketCache
import com.yoti.android.cryptocurrencychallenge.data.source.remote.model.MarketRemote
import com.yoti.android.cryptocurrencychallenge.data.utils.SystemTimestampGenerator
import com.yoti.android.cryptocurrencychallenge.utils.Mapper

class MarketRemoteToCacheMapper(private val timestampGenerator: SystemTimestampGenerator) : Mapper<MarketRemote, MarketCache>() {
    override fun mapTo(input: MarketRemote): MarketCache {
        return MarketCache(
            // We can use a better way to generating the id.
            // But for updating it was a quick way to generate unique id
            id = input.exchangeId + input.baseId + input.quoteId,
            baseId = input.baseId,
            baseSymbol = input.baseSymbol,
            exchangeId = input.exchangeId,
            percentExchangeVolume = input.percentExchangeVolume,
            priceQuote = input.priceQuote,
            priceUsd = input.priceUsd,
            quoteId = input.quoteId,
            quoteSymbol = input.quoteSymbol,
            rank = input.rank,
            tradesCount24Hr = input.tradesCount24Hr,
            updated = input.updated,
            volumeUsd24Hr = input.volumeUsd24Hr,
            insertTimestamp = timestampGenerator.generateTimestamp()
        )
    }
}
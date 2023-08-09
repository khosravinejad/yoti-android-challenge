package com.yoti.android.cryptocurrencychallenge.data.mapper

import com.yoti.android.cryptocurrencychallenge.data.source.local.model.MarketCache
import com.yoti.android.cryptocurrencychallenge.domain.model.MarketDomainModel
import com.yoti.android.cryptocurrencychallenge.utils.Mapper

class MarketCacheToDomainMapper : Mapper<MarketCache, MarketDomainModel>() {
    override fun mapTo(input: MarketCache): MarketDomainModel {
        return MarketDomainModel(
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
            volumeUsd24Hr = input.volumeUsd24Hr
        )
    }
}
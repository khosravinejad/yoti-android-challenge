package com.yoti.android.cryptocurrencychallenge.data.mapper

import com.yoti.android.cryptocurrencychallenge.data.source.local.model.AssetCache
import com.yoti.android.cryptocurrencychallenge.domain.model.AssetDomainModel
import com.yoti.android.cryptocurrencychallenge.utils.Mapper

class AssetCacheToDomainMapper : Mapper<AssetCache, AssetDomainModel>() {
    override fun mapTo(input: AssetCache): AssetDomainModel {
        return AssetDomainModel(
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
            VolumeWeightedAveragePrice24Hr = input.vwap24Hr
        )
    }
}
package com.yoti.android.cryptocurrencychallenge.presentation.mapper

import com.yoti.android.cryptocurrencychallenge.domain.model.AssetDomainModel
import com.yoti.android.cryptocurrencychallenge.presentation.model.AssetUiItem
import com.yoti.android.cryptocurrencychallenge.utils.Mapper

class AssetDomainToPresentationMapper : Mapper<AssetDomainModel, AssetUiItem>() {
    override fun mapTo(input: AssetDomainModel): AssetUiItem {
        return AssetUiItem(
            id = input.id ?: "",
            symbol = input.symbol ?: "",
            name = input.name ?: "",
            price = input.priceUsd ?: ""
        )
    }
}
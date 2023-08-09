package com.yoti.android.cryptocurrencychallenge.presentation.mapper

import com.yoti.android.cryptocurrencychallenge.domain.model.MarketDomainModel
import com.yoti.android.cryptocurrencychallenge.presentation.model.MarketUiItem
import com.yoti.android.cryptocurrencychallenge.utils.DateTimeHelper
import com.yoti.android.cryptocurrencychallenge.utils.Mapper

class MarketDomainToPresentationMapper : Mapper<MarketDomainModel, MarketUiItem>() {
    override fun mapTo(input: MarketDomainModel): MarketUiItem {
        return MarketUiItem(
            exchangeId = input.exchangeId ?: "",
            rank = input.rank ?: "",
            price = input.priceUsd ?: "",
            formattedDate = DateTimeHelper.getFormattedLocalDate(input.updated ?: 0)
        )
        // TODO use a better way to inject the date helper class
    }
}
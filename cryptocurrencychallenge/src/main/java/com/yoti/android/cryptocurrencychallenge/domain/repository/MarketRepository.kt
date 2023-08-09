package com.yoti.android.cryptocurrencychallenge.domain.repository

import com.yoti.android.cryptocurrencychallenge.domain.model.MarketDomainModel
import kotlinx.coroutines.flow.Flow

interface MarketRepository {
    suspend fun getMarketById(id: String): Result<MarketDomainModel>
}
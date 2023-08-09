package com.yoti.android.cryptocurrencychallenge.data.repository

import com.yoti.android.cryptocurrencychallenge.data.mapper.MarketCacheToDomainMapper
import com.yoti.android.cryptocurrencychallenge.data.mapper.MarketRemoteToCacheMapper
import com.yoti.android.cryptocurrencychallenge.data.source.local.dao.MarketDao
import com.yoti.android.cryptocurrencychallenge.data.source.local.model.MarketCache
import com.yoti.android.cryptocurrencychallenge.data.source.remote.CoincapService
import com.yoti.android.cryptocurrencychallenge.data.utils.TimestampGenerator
import com.yoti.android.cryptocurrencychallenge.domain.model.MarketDomainModel
import com.yoti.android.cryptocurrencychallenge.domain.repository.MarketRepository

class MarketRepositoryImpl(
    private val apiServices: CoincapService,
    private val marketDao: MarketDao,
    private val remoteToCacheMapper: MarketRemoteToCacheMapper,
    private val cacheToDomainMapper: MarketCacheToDomainMapper,
    private val timestampGenerator: TimestampGenerator
) : MarketRepository {

    override suspend fun getMarketById(id: String): Result<MarketDomainModel> {
        try {
            val market = marketDao.getMarketById(id)
            if (market == null || isDataStale(market)) {
                val markets = apiServices.getMarkets(id)
                val maxValueMarket = markets.marketData.maxByOrNull {
                    it.volumeUsd24Hr?.toDoubleOrNull() ?: Double.MIN_VALUE
                }
                if (maxValueMarket != null) {
                    val newData = remoteToCacheMapper.mapTo(maxValueMarket)
                    marketDao.insertMarket(newData)
                    return Result.success(cacheToDomainMapper.mapTo(newData))
                }
            } else {
                return Result.success(cacheToDomainMapper.mapTo(market))
            }
            return Result.failure(Exception("Cannot find the market"))
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    private fun isDataStale(marketCache: MarketCache): Boolean {
        // we can define different logics to check if data is stale
        val timeDifferenceMs = timestampGenerator.generateTimestamp() - marketCache.insertTimestamp
        return timeDifferenceMs > CACHE_DURATION_MS
    }

    companion object {
        const val CACHE_DURATION_MS = 60 * 1000 // 60 second
    }
}
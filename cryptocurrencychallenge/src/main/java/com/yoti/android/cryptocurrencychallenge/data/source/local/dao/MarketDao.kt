package com.yoti.android.cryptocurrencychallenge.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yoti.android.cryptocurrencychallenge.data.source.local.model.MarketCache
import kotlinx.coroutines.flow.Flow

@Dao
interface MarketDao {

    @Query("SELECT * FROM markets")
    fun getMarkets(): Flow<List<MarketCache>>

    @Query("SELECT * FROM markets WHERE baseId = :id")
    suspend fun getMarketById(id: String): MarketCache?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarket(market: MarketCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarkets(markets: List<MarketCache>)

    @Query("DELETE FROM markets")
    suspend fun clearTable()
}
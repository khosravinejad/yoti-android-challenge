package com.yoti.android.cryptocurrencychallenge.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yoti.android.cryptocurrencychallenge.data.source.local.model.AssetCache
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAsset(asset: AssetCache)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssets(assets: List<AssetCache>)

    @Query("SELECT * FROM assets")
    fun getAssets(): Flow<List<AssetCache>>

    @Query("SELECT insertTimestamp FROM assets ORDER BY insertTimestamp DESC LIMIT 1")
    suspend fun getLastUpdatedTimestamp(): Long?

    @Query("DELETE FROM assets")
    suspend fun clearTable()
}
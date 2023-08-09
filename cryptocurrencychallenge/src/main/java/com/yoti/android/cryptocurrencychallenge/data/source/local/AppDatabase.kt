package com.yoti.android.cryptocurrencychallenge.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yoti.android.cryptocurrencychallenge.data.source.local.dao.AssetDao
import com.yoti.android.cryptocurrencychallenge.data.source.local.dao.MarketDao
import com.yoti.android.cryptocurrencychallenge.data.source.local.model.AssetCache
import com.yoti.android.cryptocurrencychallenge.data.source.local.model.MarketCache


@Database(
    entities = [
        AssetCache::class,
        MarketCache::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "yoti_challenge.db"
    }

    abstract val assetDao: AssetDao
    abstract val marketDao: MarketDao
}
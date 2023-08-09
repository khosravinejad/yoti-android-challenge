package com.yoti.android.cryptocurrencychallenge.data.source.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.yoti.android.cryptocurrencychallenge.data.source.local.AppDatabase
import com.yoti.android.cryptocurrencychallenge.data.source.local.dao.AssetDao
import com.yoti.android.cryptocurrencychallenge.data.source.local.model.AssetCache
import com.yoti.android.cryptocurrencychallenge.data.utils.SystemTimestampGenerator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.IOException

@ExperimentalCoroutinesApi
class AssetDaoAndroidTest {

    // Google recommended to run pure Room tests as Android Test

    private lateinit var database: AppDatabase
    private lateinit var timestampGenerator: SystemTimestampGenerator
    private lateinit var sut: AssetDao

    @Before
    fun setup() {
        timestampGenerator = SystemTimestampGenerator()
        val context = ApplicationProvider.getApplicationContext<Context>()
        database =
            Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries()
                .build()
        sut = database.assetDao
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAssetAndCollectItFromDb() = runTest {
        val expected = listOf(
            AssetCache(
                id = "bitcoin",
                changePercent24Hr = "23",
                explorer = "test",
                marketCapUsd = "543",
                maxSupply = "232456",
                name = "Bitcoin",
                priceUsd = "25000",
                rank = "2",
                supply = "test",
                symbol = "BTC",
                volumeUsd24Hr = "3456",
                vwap24Hr = "34",
                insertTimestamp = timestampGenerator.generateTimestamp()
            ),
            AssetCache(
                id = "fantom",
                changePercent24Hr = "65",
                explorer = "test",
                marketCapUsd = "434356",
                maxSupply = "232456",
                name = "Fantom",
                priceUsd = "35000",
                rank = "1",
                supply = "test",
                symbol = "FTM",
                volumeUsd24Hr = "568789",
                vwap24Hr = "77895",
                insertTimestamp = timestampGenerator.generateTimestamp()
            )
        )

        sut.insertAssets(expected)

        val actual = sut.getAssets().first()
        assertEquals(expected, actual)
        assertEquals("Bitcoin", actual[0].name)
    }
}
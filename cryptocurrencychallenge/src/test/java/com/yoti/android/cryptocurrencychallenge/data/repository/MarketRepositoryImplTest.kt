package com.yoti.android.cryptocurrencychallenge.data.repository

import com.yoti.android.cryptocurrencychallenge.data.mapper.MarketCacheToDomainMapper
import com.yoti.android.cryptocurrencychallenge.data.mapper.MarketRemoteToCacheMapper
import com.yoti.android.cryptocurrencychallenge.data.source.local.dao.MarketDao
import com.yoti.android.cryptocurrencychallenge.data.source.remote.CoincapService
import com.yoti.android.cryptocurrencychallenge.data.utils.TimestampGenerator
import com.yoti.android.cryptocurrencychallenge.utils.FakeData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*
import java.io.IOException

@ExperimentalCoroutinesApi
class MarketRepositoryImplTest {
    private lateinit var apiServices: CoincapService
    private lateinit var marketDao: MarketDao
    private lateinit var remoteToCacheMapper: MarketRemoteToCacheMapper
    private lateinit var cacheToDomainMapper: MarketCacheToDomainMapper
    private lateinit var timestampGenerator: TimestampGenerator

    private lateinit var sut: MarketRepositoryImpl

    @Before
    fun setup() {
        apiServices = mock()
        marketDao = mock()
        remoteToCacheMapper = mock()
        cacheToDomainMapper = mock()
        timestampGenerator = mock()

        sut = MarketRepositoryImpl(
            apiServices,
            marketDao,
            remoteToCacheMapper,
            cacheToDomainMapper,
            timestampGenerator
        )
    }

    @Test
    fun `test getMarketById returns cached data when not stale`() = runTest {
        // Given
        val cachedMarket = FakeData.fakeMarketCache()[9]
        val marketId = cachedMarket.id
        val cachedDomainModel = FakeData.fakeMarketDomainModel()[9]

        given(marketDao.getMarketById(marketId)).willReturn(cachedMarket)
        given(cacheToDomainMapper.mapTo(cachedMarket)).willReturn(cachedDomainModel)
        given(apiServices.getMarkets(marketId)).willReturn(FakeData.fakeMarketRemoteResponse())

        // When
        val result = sut.getMarketById(marketId)

        // Then
        verify(marketDao, never()).insertMarket(cachedMarket)
        assertEquals(Result.success(cachedDomainModel), result)
    }

    @Test
    fun `test getMarketById fetches new data when cached data is stale or null`() = runTest {
        // Given
        val remoteResponse = FakeData.fakeMarketRemoteResponse()
        val marketRemoteToStore = FakeData.fakeMarketRemoteModel()[9] // Max in volumeUsd24Hr
        val marketCacheToStore = FakeData.fakeMarketCache()[9]
        val marketDomain = FakeData.fakeMarketDomainModel()[9]

        given(marketDao.getMarketById(anyString())).willReturn(null)
        given(apiServices.getMarkets(anyString())).willReturn(remoteResponse)
        given(remoteToCacheMapper.mapTo(marketRemoteToStore)).willReturn(marketCacheToStore)
        given(cacheToDomainMapper.mapTo(marketCacheToStore)).willReturn(marketDomain)

        // When
        val result = sut.getMarketById(anyString())

        // Then
        verify(marketDao).insertMarket(marketCacheToStore)
        assertEquals(Result.success(marketDomain), result)
    }

    @Test
    fun `test getMarketById returns failure when an exception occurs`() = runTest {
        // Given
        val cachedMarket = FakeData.fakeMarketCache()[3]
        val marketId = cachedMarket.id
        val cachedDomainModel = FakeData.fakeMarketDomainModel()[3]

        given(marketDao.getMarketById(marketId)).willReturn(null)
        given(cacheToDomainMapper.mapTo(cachedMarket)).willReturn(cachedDomainModel)
        val expectedException = IOException()
        given(apiServices.getMarkets(marketId)).will { throw expectedException }

        // When
        val result = sut.getMarketById(marketId)

        // Then
        verify(marketDao, never()).insertMarket(cachedMarket)
        assertEquals(expectedException, result.exceptionOrNull())
    }
}
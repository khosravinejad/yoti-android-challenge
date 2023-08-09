package com.yoti.android.cryptocurrencychallenge.data.repository

import com.yoti.android.cryptocurrencychallenge.data.mapper.AssetCacheToDomainMapper
import com.yoti.android.cryptocurrencychallenge.data.mapper.AssetRemoteToCacheMapper
import com.yoti.android.cryptocurrencychallenge.data.source.local.dao.AssetDao
import com.yoti.android.cryptocurrencychallenge.data.source.remote.CoincapService
import com.yoti.android.cryptocurrencychallenge.data.utils.SystemTimestampGenerator
import com.yoti.android.cryptocurrencychallenge.utils.FakeData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class AssetRepositoryImplTest {

    private lateinit var assetDao: AssetDao
    private lateinit var coincapService: CoincapService
    private lateinit var remoteToCacheMapper: AssetRemoteToCacheMapper
    private lateinit var cacheToDomainMapper: AssetCacheToDomainMapper
    private lateinit var timestampGenerator: SystemTimestampGenerator

    private lateinit var sut: AssetRepositoryImpl

    @Before
    fun setup() {
        coincapService = mock()
        remoteToCacheMapper = mock()
        cacheToDomainMapper = mock()
        timestampGenerator = mock()

        assetDao = mock()

        sut = AssetRepositoryImpl(
            coincapService,
            assetDao,
            remoteToCacheMapper,
            cacheToDomainMapper,
            timestampGenerator
        )
    }

    @Test
    fun `test assetsFlow emits cached assets when not stale`() = runTest {
        // Given
        val cachedAssets = FakeData.fakeAssetCache()
        given(assetDao.getAssets()).willReturn(flowOf(cachedAssets))
        given(cacheToDomainMapper.mapTo(cachedAssets)).willReturn(FakeData.fakeAssetDomainModel())
        given(assetDao.getLastUpdatedTimestamp()).willReturn(FakeData.currentMockTimeStamp)

        // When
        val results = sut.assetsFlow.first()

        // Then
        assertEquals(Result.success(FakeData.fakeAssetDomainModel()), results)
    }

    @Test
    fun `test assetsFlow emits refreshed assets when data is stale`() = runTest {
        // Given
        val cachedAssets = FakeData.fakeAssetCache()
        val refreshedAssetData = FakeData.fakeAssetRemoteResponse()
        val refreshedAssets = FakeData.fakeAssetDomainModel()

        given(assetDao.getAssets()).willReturn(flowOf(cachedAssets))
        given(cacheToDomainMapper.mapTo(cachedAssets)).willReturn(FakeData.fakeAssetDomainModel())
        given(assetDao.getLastUpdatedTimestamp()).willReturn(0)
        given(timestampGenerator.generateTimestamp()).willReturn(FakeData.currentMockTimeStamp)
        given(coincapService.getAssets()).willReturn(refreshedAssetData)
        given(remoteToCacheMapper.mapTo(refreshedAssetData.assetData)).willReturn(FakeData.fakeAssetCache())

        // When
        val results = sut.assetsFlow.first()

        // Then
        assertTrue(results.isSuccess)
        assertEquals(FakeData.fakeAssetDomainModel(), results.getOrNull())
        assertTrue(results.isSuccess)
        assertEquals(refreshedAssets, results.getOrNull())
        verify(assetDao).insertAssets(anyList())
    }

    @Test
    fun `test refreshAssets`() = runTest {
        // Mock data and behavior
        val assetRemoteList = FakeData.fakeAssetRemoteModel(2)
        val assetCacheList = FakeData.fakeAssetCache(2)
        given(coincapService.getAssets()).willReturn(FakeData.fakeAssetRemoteResponse(2))
        given(remoteToCacheMapper.mapTo(assetRemoteList)).willReturn(assetCacheList)

        // Call the function
        sut.refreshAssets()

        // Assertions
        verify(assetDao).insertAssets(assetCacheList)
    }

}
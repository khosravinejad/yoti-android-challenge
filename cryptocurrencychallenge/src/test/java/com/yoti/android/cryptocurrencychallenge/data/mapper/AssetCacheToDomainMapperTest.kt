package com.yoti.android.cryptocurrencychallenge.data.mapper

import com.yoti.android.cryptocurrencychallenge.data.utils.TimestampGenerator
import com.yoti.android.cryptocurrencychallenge.utils.FakeData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class AssetCacheToDomainMapperTest {

    private lateinit var sut: AssetCacheToDomainMapper
    private lateinit var timestampGenerator: TimestampGenerator

    @Before
    fun setup() {
        timestampGenerator = mock()
        sut = AssetCacheToDomainMapper()
    }

    @Test
    fun `mapTo maps correctly`() {
        // Given
        given(timestampGenerator.generateTimestamp()).willReturn(FakeData.currentMockTimeStamp)
        val assetCache = FakeData.fakeAssetCache()

        // When
        val result = sut.mapTo(assetCache)

        // Then
        val expected = FakeData.fakeAssetDomainModel()
        assertEquals(expected, result)
    }
}
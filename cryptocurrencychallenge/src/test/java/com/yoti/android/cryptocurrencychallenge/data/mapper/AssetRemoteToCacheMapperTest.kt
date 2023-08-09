package com.yoti.android.cryptocurrencychallenge.data.mapper

import com.yoti.android.cryptocurrencychallenge.data.utils.SystemTimestampGenerator
import com.yoti.android.cryptocurrencychallenge.utils.FakeData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

class AssetRemoteToCacheMapperTest {

    private lateinit var timestampGenerator: SystemTimestampGenerator
    private lateinit var sut: AssetRemoteToCacheMapper

    @Before
    fun setup() {
        timestampGenerator = mock()
        sut = AssetRemoteToCacheMapper(timestampGenerator)
    }

    @Test
    fun `mapTo maps correctly`() {
        // Given
        given(timestampGenerator.generateTimestamp()).willReturn(FakeData.currentMockTimeStamp)

        val assetRemote = FakeData.fakeAssetRemoteModel()

        // When
        val result = sut.mapTo(assetRemote)

        // Then
        val expected = FakeData.fakeAssetCache()
        assertEquals(expected, result)
    }
}
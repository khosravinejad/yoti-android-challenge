package com.yoti.android.cryptocurrencychallenge.presentation.viewmodel

import com.yoti.android.cryptocurrencychallenge.domain.repository.AssetRepository
import com.yoti.android.cryptocurrencychallenge.presentation.mapper.AssetDomainToPresentationMapper
import com.yoti.android.cryptocurrencychallenge.utils.FakeData
import com.yoti.android.cryptocurrencychallenge.utils.TestContextProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.io.IOException

@ExperimentalCoroutinesApi
class AssetsViewModelTest {

    private val dispatcher = TestContextProvider()
    private lateinit var assetRepository: AssetRepository
    private lateinit var domainToPresenterMapper: AssetDomainToPresentationMapper
    private lateinit var sut: AssetsViewModel

    @Before
    fun setup() {
        assetRepository = mock()
        domainToPresenterMapper = mock()

        sut = AssetsViewModel(
            dispatcher,
            assetRepository,
            domainToPresenterMapper
        )
    }

    @Test
    fun `loadAssets emits Success state when assetsFlow emits success`() = runTest {
        val domainAssets = FakeData.fakeAssetDomainModel()
        val expectedResult = FakeData.fakeAssetUiItem()
        given(assetRepository.assetsFlow).willReturn(flowOf(Result.success(domainAssets)))
        given(domainToPresenterMapper.mapTo(domainAssets)).willReturn(expectedResult)

        sut.loadAssets()

        Assert.assertEquals(AssetsState.Success(expectedResult), sut.assetsState.value)
    }

    @Test
    fun `loadAssets emits Error state when assetsFlow emits failure`() = runTest {
        given(assetRepository.assetsFlow).willReturn(flowOf(Result.failure(IOException())))

        sut.loadAssets()

        Assert.assertEquals(AssetsState.Error(AssetsError.NetworkError), sut.assetsState.value)
        Assert.assertEquals(false, sut.refreshingState.value)
    }

    @Test
    fun `refreshAssets handles failure`() = runTest {
        // Given
        val exception = Exception("Unknown sample Error")
        given(assetRepository.refreshAssets()).willReturn(Result.failure(exception))

        // When
        sut.refreshAssets()

        // Then
        Assert.assertEquals(
            AssetsState.Error(AssetsError.UnknownError("Unknown sample Error")),
            sut.assetsState.value
        )
        Assert.assertEquals(false, sut.refreshingState.value)
    }

}
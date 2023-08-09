package com.yoti.android.cryptocurrencychallenge.ui.assets

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.yoti.android.cryptocurrencychallenge.HiltTestActivity
import com.yoti.android.cryptocurrencychallenge.R
import com.yoti.android.cryptocurrencychallenge.presentation.model.AssetUiItem
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.AssetsError
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.AssetsState
import com.yoti.android.cryptocurrencychallenge.presentation.viewmodel.AssetsViewModel
import com.yoti.android.cryptocurrencychallenge.ui.market.MarketFragmentDirections
import com.yoti.android.cryptocurrencychallenge.utils.launchFragmentInHiltContainer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.components.SingletonComponent
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton

@HiltAndroidTest
@RunWith(AndroidJUnit4ClassRunner::class)
class AssetsFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityScenarioRule: ActivityScenarioRule<HiltTestActivity> =
        ActivityScenarioRule(HiltTestActivity::class.java)

    private val mockNavController = mockk<NavController>(relaxed = true)

    @Before
    fun prepareTest() {
        MockKAnnotations.init(this)
        hiltRule.inject()
    }

    @Inject
    lateinit var assetsViewModel: AssetsViewModel

    @Module
    @InstallIn(SingletonComponent::class)
    object TestModule {
        @Singleton
        @Provides
        fun provideAssetsViewModel(): AssetsViewModel = mockk(relaxed = true)
    }

    @Test
    fun testLoadingState() {
        // Given
        every { assetsViewModel.assetsState.value } returns AssetsState.Loading
        launchFragmentInHiltContainer<AssetsFragment>()

        // Then
        onView(withId(R.id.progressBarAssets)).check(matches(isDisplayed()))
        onView(withId(R.id.errorLayout)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testSuccessState() {
        // Given
        val fakeData =
            listOf(AssetUiItem(id = "1", symbol = "BTC", name = "Bitcoin", price = "1000"))
        every { assetsViewModel.assetsState.value } returns AssetsState.Success(fakeData)
        launchFragmentInHiltContainer<AssetsFragment>()

        // Then
        onView(withId(R.id.progressBarAssets)).check(matches(isDisplayed()))
        onView(withId(R.id.errorLayout)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testErrorState() {
        // Given
        every { assetsViewModel.assetsState.value } returns AssetsState.Error(AssetsError.NetworkError)
        launchFragmentInHiltContainer<AssetsFragment>()

        // Then
        onView(withId(R.id.errorLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.refreshLayout)).check(matches(not(isDisplayed())))
    }

    @Test
    fun testItemClickNavigatesToMarketFragment() {
        // Given
        val fakeData =
            listOf(AssetUiItem(id = "1", symbol = "BTC", name = "Bitcoin", price = "1000"))
        every { assetsViewModel.assetsState.value } returns AssetsState.Success(fakeData)
        launchFragmentInHiltContainer<AssetsFragment>()
        onView(withId(R.id.recyclerViewAssets)).perform(click())

        // Then
        val action = MarketFragmentDirections.startDetailsFragment(fakeData[0].id)
        verify { mockNavController.navigate(action) }
    }

}
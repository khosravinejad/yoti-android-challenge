package com.yoti.android.cryptocurrencychallenge.di

import com.yoti.android.cryptocurrencychallenge.presentation.mapper.AssetDomainToPresentationMapper
import com.yoti.android.cryptocurrencychallenge.presentation.mapper.MarketDomainToPresentationMapper
import com.yoti.android.cryptocurrencychallenge.utils.CoroutineContextProvider
import com.yoti.android.cryptocurrencychallenge.utils.CoroutineContextProviderImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PresentationModule {

    @Provides
    @Singleton
    fun provideCoroutineContextProvider(contextProvider: CoroutineContextProviderImp): CoroutineContextProvider =
        contextProvider

    @Provides
    fun provideAssetPresentationMapper() = AssetDomainToPresentationMapper()

    @Provides
    fun provideMarketPresentationMapper() = MarketDomainToPresentationMapper()
}
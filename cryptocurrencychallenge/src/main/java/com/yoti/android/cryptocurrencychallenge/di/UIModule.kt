package com.yoti.android.cryptocurrencychallenge.di

import com.yoti.android.cryptocurrencychallenge.ui.assets.AssetsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
object UIModule {

    @Provides
    fun provideAssetsAdapter() = AssetsAdapter()
}
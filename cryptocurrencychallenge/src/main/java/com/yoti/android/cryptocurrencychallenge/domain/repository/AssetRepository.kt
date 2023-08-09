package com.yoti.android.cryptocurrencychallenge.domain.repository

import com.yoti.android.cryptocurrencychallenge.domain.model.AssetDomainModel
import kotlinx.coroutines.flow.Flow

interface AssetRepository {

    // Define it as a flow to keep the latest updated value
    val assetsFlow: Flow<Result<List<AssetDomainModel>>>
    suspend fun refreshAssets(): Result<Unit>
}
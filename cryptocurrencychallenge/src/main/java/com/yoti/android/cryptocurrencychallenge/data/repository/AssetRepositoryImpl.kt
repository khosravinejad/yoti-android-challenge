package com.yoti.android.cryptocurrencychallenge.data.repository

import com.yoti.android.cryptocurrencychallenge.data.mapper.AssetCacheToDomainMapper
import com.yoti.android.cryptocurrencychallenge.data.mapper.AssetRemoteToCacheMapper
import com.yoti.android.cryptocurrencychallenge.data.source.local.dao.AssetDao
import com.yoti.android.cryptocurrencychallenge.data.source.remote.CoincapService
import com.yoti.android.cryptocurrencychallenge.data.utils.SystemTimestampGenerator
import com.yoti.android.cryptocurrencychallenge.domain.model.AssetDomainModel
import com.yoti.android.cryptocurrencychallenge.domain.repository.AssetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class AssetRepositoryImpl(
    private val apiServices: CoincapService,
    private val assetDao: AssetDao,
    private val remoteToCacheMapper: AssetRemoteToCacheMapper,
    private val cacheToDomainMapper: AssetCacheToDomainMapper,
    private val timestampGenerator: SystemTimestampGenerator
) : AssetRepository {

    override val assetsFlow: Flow<Result<List<AssetDomainModel>>>
        get() = assetDao.getAssets()
            .map {
                cacheToDomainMapper.mapTo(it)
            }
            .flatMapLatest { cachedAssets ->
                try {
                    val cacheTimestamp = assetDao.getLastUpdatedTimestamp()
                    if (cacheTimestamp == null || isDataStale(cacheTimestamp)) {
                        val refreshedAsset = apiServices.getAssets()
                        assetDao.insertAssets(remoteToCacheMapper.mapTo(refreshedAsset.assetData))
                    }
                    flowOf(Result.success(cachedAssets))
                } catch (e: Exception) {
                    flowOf(Result.failure(e))
                }
            }

    override suspend fun refreshAssets(): Result<Unit> {
        return try {
            val refreshedAsset = apiServices.getAssets()
            assetDao.insertAssets(remoteToCacheMapper.mapTo(refreshedAsset.assetData))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun isDataStale(timestamp: Long): Boolean {
        val timeDifferenceMs = timestampGenerator.generateTimestamp() - timestamp
        return timeDifferenceMs > CACHE_DURATION_MS
    }

    companion object {
        const val CACHE_DURATION_MS = 60 * 60 * 1000 // 60 min
    }
}
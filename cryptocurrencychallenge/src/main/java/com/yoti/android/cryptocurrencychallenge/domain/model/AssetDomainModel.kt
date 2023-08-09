package com.yoti.android.cryptocurrencychallenge.domain.model

data class AssetDomainModel(
    val changePercent24Hr: String?,
    val explorer: String?,
    val id: String?,
    val marketCapUsd: String?,
    val maxSupply: String?,
    val name: String?,
    val priceUsd: String?,
    val rank: String?,
    val supply: String?,
    val symbol: String?,
    val volumeUsd24Hr: String?,
    val VolumeWeightedAveragePrice24Hr: String?
)
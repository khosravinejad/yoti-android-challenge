package com.yoti.android.cryptocurrencychallenge.di

import android.app.Application
import androidx.room.Room
import com.yoti.android.cryptocurrencychallenge.BuildConfig
import com.yoti.android.cryptocurrencychallenge.data.mapper.AssetCacheToDomainMapper
import com.yoti.android.cryptocurrencychallenge.data.mapper.AssetRemoteToCacheMapper
import com.yoti.android.cryptocurrencychallenge.data.mapper.MarketCacheToDomainMapper
import com.yoti.android.cryptocurrencychallenge.data.mapper.MarketRemoteToCacheMapper
import com.yoti.android.cryptocurrencychallenge.data.repository.AssetRepositoryImpl
import com.yoti.android.cryptocurrencychallenge.data.repository.MarketRepositoryImpl
import com.yoti.android.cryptocurrencychallenge.data.source.local.AppDatabase
import com.yoti.android.cryptocurrencychallenge.data.source.remote.CoincapService
import com.yoti.android.cryptocurrencychallenge.data.source.remote.CoincapService.Companion.CAPCOIN_ENDPOINT_HOST
import com.yoti.android.cryptocurrencychallenge.data.utils.SystemTimestampGenerator
import com.yoti.android.cryptocurrencychallenge.domain.repository.AssetRepository
import com.yoti.android.cryptocurrencychallenge.domain.repository.MarketRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    fun provideOkHttpClient() = OkHttpClient.Builder().build()

    @Provides
    @Singleton
    fun provideCoincapService(okHttpClient: OkHttpClient): CoincapService {
        val okHttpBuilder = okHttpClient.newBuilder()
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpBuilder.addInterceptor(httpLoggingInterceptor)
        }
        return Retrofit.Builder()
            .baseUrl(CAPCOIN_ENDPOINT_HOST)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpBuilder.build())
            .build()
            .create(CoincapService::class.java)
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseName(): String {
        return AppDatabase.DB_NAME
    }

    @Provides
    fun provideDatabase(@DatabaseInfo dbName: String, application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, dbName)
            .fallbackToDestructiveMigration().allowMainThreadQueries().build()
    }

    @Provides
    fun provideAssetRemoteToCacheMapper(timestampGenerator: SystemTimestampGenerator) =
        AssetRemoteToCacheMapper(timestampGenerator)

    @Provides
    fun provideAssetCacheToDomainMapper() = AssetCacheToDomainMapper()

    @Provides
    fun provideMarketRemoteToCacheMapper(timestampGenerator: SystemTimestampGenerator) =
        MarketRemoteToCacheMapper(timestampGenerator)

    @Provides
    fun provideMarketCacheToDomainMapper() = MarketCacheToDomainMapper()

    @Provides
    fun provideTimestampGenerator(): SystemTimestampGenerator = SystemTimestampGenerator()

    @Provides
    @Singleton
    fun provideAssetRepository(
        apiServices: CoincapService,
        database: AppDatabase,
        remoteToCacheMapper: AssetRemoteToCacheMapper,
        cacheToDomainMapper: AssetCacheToDomainMapper,
        timestampGenerator: SystemTimestampGenerator
    ): AssetRepository = AssetRepositoryImpl(
        apiServices = apiServices,
        assetDao = database.assetDao,
        remoteToCacheMapper = remoteToCacheMapper,
        cacheToDomainMapper = cacheToDomainMapper,
        timestampGenerator = timestampGenerator
    )

    @Provides
    @Singleton
    fun provideMarketRepository(
        apiServices: CoincapService,
        database: AppDatabase,
        remoteToCacheMapper: MarketRemoteToCacheMapper,
        cacheToDomainMapper: MarketCacheToDomainMapper,
        timestampGenerator: SystemTimestampGenerator
    ): MarketRepository = MarketRepositoryImpl(
        apiServices = apiServices,
        marketDao = database.marketDao,
        remoteToCacheMapper = remoteToCacheMapper,
        cacheToDomainMapper = cacheToDomainMapper,
        timestampGenerator = timestampGenerator
    )
}
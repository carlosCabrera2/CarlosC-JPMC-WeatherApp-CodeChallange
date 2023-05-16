package com.example.carlosc_jpmc_weatherapp_codechallange.DependencyInjection

import com.example.carlosc_jpmc_weatherapp_codechallange.REST.AppRepository
import com.example.carlosc_jpmc_weatherapp_codechallange.REST.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun AppRepository(
        appRepositoryImpl: AppRepositoryImpl
    ):AppRepository
}
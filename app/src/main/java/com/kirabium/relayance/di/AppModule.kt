package com.kirabium.relayance.di

import com.kirabium.relayance.repository.CustomerRepository
import com.kirabium.relayance.service.CustomerApi
import com.kirabium.relayance.service.CustomerFakeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCustomerApi(): CustomerApi{
        return CustomerFakeApi()
    }

    @Provides
    @Singleton
    fun provideRepository(customerApi : CustomerApi): CustomerRepository{
        return CustomerRepository(customerApi)
    }

}
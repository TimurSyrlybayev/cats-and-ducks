package com.example.catsandducksapp.data.network

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named

@Module
class ModuleNew {

    @Provides
    @Named("providerApiCats")
    fun providerApiCats() : CatsAndDucksApi {
        return Retrofit.Builder()
            .baseUrl("https://thatcopy.pw/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(CatsAndDucksApi::class.java)
    }

    @Provides
    @Named("providerApiDucks")
    fun providerApiDucks() : CatsAndDucksApi {
        return Retrofit.Builder()
            .baseUrl("https://random-d.uk/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(CatsAndDucksApi::class.java)
    }

}
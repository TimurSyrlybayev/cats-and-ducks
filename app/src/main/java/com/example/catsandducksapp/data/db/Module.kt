package com.example.catsandducksapp.data.db

import com.example.catsandducksapp.CatsAndDucksApplication
import dagger.Module
import dagger.Provides

@Module
class Module {

    @Provides
    fun provideDao(): CatsAndDucksDao {
        return CatsAndDucksApplication.database.catsAndDucksDao()
    }

    @Provides
    fun provideDatabase(): CatsAndDucksDatabase {
        return CatsAndDucksApplication.database
    }

}
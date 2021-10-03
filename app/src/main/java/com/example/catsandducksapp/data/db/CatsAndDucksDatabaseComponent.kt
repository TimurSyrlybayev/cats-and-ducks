package com.example.catsandducksapp.data.db

import dagger.Component

@Component(modules = [Module::class])
interface CatsAndDucksDatabaseComponent {

    fun getDatabase(): CatsAndDucksDatabase

}
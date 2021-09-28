package com.example.catsandducksapp.data.network

import dagger.Component

@Component(modules = [ModuleNew::class])
interface DucksRetrofitClientComponent {

    fun getDucksRetrofitClient(): DucksRetrofitClient

}
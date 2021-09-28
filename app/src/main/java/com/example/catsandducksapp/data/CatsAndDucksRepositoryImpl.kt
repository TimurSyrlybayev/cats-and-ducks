package com.example.catsandducksapp.data

import androidx.lifecycle.LiveData
import com.example.catsandducksapp.R
import com.example.catsandducksapp.data.network.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class CatsAndDucksRepositoryImpl : CatsAndDucksRepository {

    private val catsRetrofitClient =
        DaggerCatsRetrofitClientComponent.create().getCatsRetrofitClient()

    private val ducksRetrofitClient =
        DaggerDucksRetrofitClientComponent.create().getDucksRetrofitClient()

    override fun getImage(buttonId: Int): LiveData<String> {
        return when (buttonId) {
            R.id.buttonCats -> { catsRetrofitClient.getCatImage() }
            R.id.buttonDucks -> { ducksRetrofitClient.getDuckImage() }
            else -> throw Exception("Wrong buttonId: $buttonId")
        }
    }
}
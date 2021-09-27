package com.example.catsandducksapp.data

import androidx.lifecycle.LiveData
import com.example.catsandducksapp.R
import com.example.catsandducksapp.data.network.CatsAndDucksApi
import com.example.catsandducksapp.data.network.CatsRetrofitClient
import com.example.catsandducksapp.data.network.DucksRetrofitClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class CatsAndDucksRepositoryImpl : CatsAndDucksRepository {

    private val catsRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://thatcopy.pw/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private val catsApi: CatsAndDucksApi = catsRetrofit.create(CatsAndDucksApi::class.java)
    private val catsRetrofitClient = CatsRetrofitClient(catsApi)

    private val ducksRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://random-d.uk/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private val ducksApi: CatsAndDucksApi = ducksRetrofit.create(CatsAndDucksApi::class.java)
    private val ducksRetrofitClient = DucksRetrofitClient(ducksApi)

    override fun getImage(buttonId: Int): LiveData<String> {
        return when (buttonId) {
            R.id.buttonCats -> { catsRetrofitClient.getCatImage() }
            R.id.buttonDucks -> { ducksRetrofitClient.getDuckImage() }
            else -> throw Exception("Wrong buttonId: $buttonId")
        }
    }
}
package com.example.catsandducksapp.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class CatsRetrofitClient @Inject constructor(@Named("providerApiCats") val catsApi: CatsAndDucksApi) {

    fun getCatImage(): LiveData<String> {
        val catResponseLiveData: MutableLiveData<String> = MutableLiveData()
        val catImageRequest: Call<String> = catsApi.getCatImage()

        catImageRequest.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                catResponseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                throw Error("Connection error. Call: $call, throwable: $t")
            }
        })
        return catResponseLiveData
    }
}
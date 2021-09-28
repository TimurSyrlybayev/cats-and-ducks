package com.example.catsandducksapp.data.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named

class DucksRetrofitClient @Inject constructor(@Named("providerApiDucks") val ducksApi: CatsAndDucksApi) {

    fun getDuckImage(): LiveData<String> {
        val duckResponseLiveData: MutableLiveData<String> = MutableLiveData()
        val duckImageRequest: Call<String> = ducksApi.getDuckImage()

        duckImageRequest.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                duckResponseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                throw Error("Connection error. Call: $call, throwable: $t")
            }
        })
        return duckResponseLiveData
    }
}
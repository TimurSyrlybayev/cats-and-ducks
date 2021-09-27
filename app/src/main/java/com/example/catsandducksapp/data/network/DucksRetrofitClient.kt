package com.example.catsandducksapp.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "DucksRetrofitClient"

class DucksRetrofitClient(val ducksApi: CatsAndDucksApi) {

    fun getDuckImage(): LiveData<String> {
        val duckResponseLiveData: MutableLiveData<String> = MutableLiveData()
        val duckImageRequest: Call<String> = ducksApi.getDuckImage()

        duckImageRequest.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                Log.d(TAG, "Respose received: ${response.body()}")
                duckResponseLiveData.value = response.body()
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d(TAG, "Failed to fetch photos", t)
            }
        })
        return duckResponseLiveData
    }
}
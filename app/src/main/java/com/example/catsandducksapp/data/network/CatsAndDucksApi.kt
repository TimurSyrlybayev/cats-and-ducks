package com.example.catsandducksapp.data.network

import retrofit2.Call
import retrofit2.http.GET
import javax.inject.Inject

interface CatsAndDucksApi {

    @GET("/catapi/rest/")
    fun getCatImage(): Call<String>

    @GET("/api/random")
    fun getDuckImage(): Call<String>
}
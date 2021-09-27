package com.example.catsandducksapp.data

import androidx.lifecycle.LiveData

interface CatsAndDucksRepository {

    fun getImage(buttonId: Int): LiveData<String>

}
package com.example.catsandducksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.catsandducksapp.data.CatsAndDucksRepository
import com.example.catsandducksapp.data.CatsAndDucksRepositoryImpl

class MainViewModel(private val repository: CatsAndDucksRepository = CatsAndDucksRepositoryImpl()): ViewModel() {

    fun getImage(buttonId: Int): LiveData<String> {
        return repository.getImage(buttonId)
    }

}
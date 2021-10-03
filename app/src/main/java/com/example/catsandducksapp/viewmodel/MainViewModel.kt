package com.example.catsandducksapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.catsandducksapp.data.CatsAndDucksRepository
import com.example.catsandducksapp.data.CatsAndDucksRepositoryImpl
import com.example.catsandducksapp.data.model.ImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: CatsAndDucksRepository = CatsAndDucksRepositoryImpl()): ViewModel() {

    fun getImage(buttonId: Int): LiveData<String> {
        return repository.getImage(buttonId)
    }

    fun getSavedImages(): LiveData<MutableList<ImageItem>> {
        return repository.getSavedImages()
    }

    fun saveImage(imageItem: ImageItem) {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkIfImageAlreadySaved(imageItem.imageUrl) != 1L) {
                repository.saveImage(imageItem)
            }
        }
    }

    fun clearAllImages() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearAllSavedImages()
        }
    }

    fun deleteImage(imageItem: ImageItem) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteOneSavedImage(imageItem)
        }
    }

    private fun checkIfImageAlreadySaved(imageUrl: String): Long {
        return repository.checkIfRecordExists(imageUrl)
    }

}
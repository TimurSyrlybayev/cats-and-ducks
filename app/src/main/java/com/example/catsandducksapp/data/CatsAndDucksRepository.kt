package com.example.catsandducksapp.data

import androidx.lifecycle.LiveData
import com.example.catsandducksapp.data.model.ImageItem

interface CatsAndDucksRepository {

    fun getImage(buttonId: Int): LiveData<String>

    fun getSavedImages(): LiveData<MutableList<ImageItem>>

    suspend fun saveImage(imageItem: ImageItem)

    suspend fun clearAllSavedImages()

    suspend fun deleteOneSavedImage(imageItem: ImageItem)

    fun checkIfRecordExists(imageUrl: String): Long

}
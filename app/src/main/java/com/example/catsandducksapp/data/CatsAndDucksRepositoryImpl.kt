package com.example.catsandducksapp.data

import androidx.lifecycle.LiveData
import com.example.catsandducksapp.R
import com.example.catsandducksapp.data.db.DaggerCatsAndDucksDatabaseComponent
import com.example.catsandducksapp.data.model.ImageItem
import com.example.catsandducksapp.data.network.*
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

class CatsAndDucksRepositoryImpl : CatsAndDucksRepository {

    private val catsRetrofitClient =
        DaggerCatsRetrofitClientComponent.create().getCatsRetrofitClient()

    private val ducksRetrofitClient =
        DaggerDucksRetrofitClientComponent.create().getDucksRetrofitClient()

    private val database =
        DaggerCatsAndDucksDatabaseComponent.create().getDatabase()

    override fun getImage(buttonId: Int): LiveData<String> {
        return when (buttonId) {
            R.id.buttonCats -> { catsRetrofitClient.getCatImage() }
            R.id.buttonDucks -> { ducksRetrofitClient.getDuckImage() }
            else -> throw Exception("Wrong buttonId: $buttonId")
        }
    }

    override fun getSavedImages(): LiveData<MutableList<ImageItem>> {
        return database.catsAndDucksDao().retrieveAllRecords()
    }

    override suspend fun saveImage(imageItem: ImageItem) {
        database.catsAndDucksDao().insertRecord(imageItem)
    }

    override suspend fun clearAllSavedImages() {
        database.catsAndDucksDao().clearRecords()
    }

    override suspend fun deleteOneSavedImage(imageItem: ImageItem) {
        database.catsAndDucksDao().deleteSingleRecord(imageItem)
    }

    override fun checkIfRecordExists(imageUrl: String): Long {
        return database.catsAndDucksDao().checkIfRecordExists(imageUrl)
    }
}
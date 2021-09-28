package com.example.catsandducksapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.catsandducksapp.data.model.ImageItem
import com.example.catsandducksapp.data.model.KEY_IMAGE_URL
import com.example.catsandducksapp.data.model.TABLE_NAME

@Dao
interface CatsAndDucksDao {

    @Query("SELECT * FROM $TABLE_NAME")
    fun retrieveAllRecords(): LiveData<MutableList<ImageItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecord(imageItem: ImageItem)

    @Query("DELETE FROM $TABLE_NAME")
    fun clearRecords()

    @Delete
    fun deleteSingleRecord(imageItem: ImageItem)

    @Query("SELECT EXISTS(SELECT 1 FROM $TABLE_NAME WHERE $KEY_IMAGE_URL=:imageUrl)")
    fun checkIfRecordExists(imageUrl: String): Long
}
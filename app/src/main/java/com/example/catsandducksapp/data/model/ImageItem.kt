package com.example.catsandducksapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val DATABASE_VERSION = 1
const val DATABASE_NAME = "catsandducksdb.db"
const val TABLE_NAME = "catsandduckstable"
const val KEY_ID = "imageid"
const val KEY_IMAGE_URL = "imageurl"
const val KEY_IMAGE_ADDED_TIME = "imagetime"

@Entity(tableName = TABLE_NAME)
data class ImageItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = KEY_ID)
    var id: Long,
    @ColumnInfo(name = KEY_IMAGE_URL)
    var imageUrl: String = "",
    @ColumnInfo(name = KEY_IMAGE_ADDED_TIME)
    var imageTime: String = ""
)

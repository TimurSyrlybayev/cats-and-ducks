package com.example.catsandducksapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.catsandducksapp.data.model.DATABASE_VERSION
import com.example.catsandducksapp.data.model.ImageItem

@Database(entities = [ImageItem::class], version = DATABASE_VERSION)
abstract class CatsAndDucksDatabase : RoomDatabase() {

    abstract fun catsAndDucksDao(): CatsAndDucksDao

}
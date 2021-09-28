package com.example.catsandducksapp

import android.app.Application
import androidx.room.Room
import com.example.catsandducksapp.data.db.CatsAndDucksDatabase
import com.example.catsandducksapp.data.model.DATABASE_NAME

class CatsAndDucksApplication : Application() {

    companion object {
        lateinit var database: CatsAndDucksDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            CatsAndDucksDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}
package com.example.narcosdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.narcosdb.dao.DrugDao
import com.example.narcosdb.entity.Drug

@Database(entities = [Drug::class], version = 1, exportSchema = false) //add as array if multiple
abstract class DrugDatabase : RoomDatabase() {
    abstract fun drugDao(): DrugDao

    companion object {
        @Volatile
        private var INSTANCE : DrugDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DrugDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

                val instance = Room.databaseBuilder(context.applicationContext, DrugDatabase::class.java, "drug")
                        .build()
            INSTANCE = instance
            return instance
        }
    }
}
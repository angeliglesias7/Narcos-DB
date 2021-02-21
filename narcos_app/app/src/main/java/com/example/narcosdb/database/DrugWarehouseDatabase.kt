package com.example.narcosdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.narcosdb.dao.DrugWarehouseDao
import com.example.narcosdb.entity.DrugWarehouse

@Database(entities = [DrugWarehouse::class], version = 1, exportSchema = false) //add as array if multiple
abstract class DrugWarehouseDatabase : RoomDatabase() {
    abstract fun drugWarehouseDao(): DrugWarehouseDao

    companion object {
        @Volatile
        private var INSTANCE : DrugWarehouseDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DrugWarehouseDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            val instance = Room.databaseBuilder(context.applicationContext, DrugWarehouseDatabase::class.java, "drugwarehouse")
                .build()
            INSTANCE = instance
            return instance
        }
    }
}
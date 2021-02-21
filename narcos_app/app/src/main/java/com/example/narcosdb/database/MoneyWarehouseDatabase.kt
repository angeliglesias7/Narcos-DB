package com.example.narcosdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.narcosdb.dao.MoneyWarehouseDao
import com.example.narcosdb.entity.MoneyWarehouse

@Database(entities = [MoneyWarehouse::class], version = 1, exportSchema = false) //add as array if multiple
abstract class MoneyWarehouseDatabase : RoomDatabase() {
    abstract fun moneyWarehouseDao(): MoneyWarehouseDao

    companion object {
        @Volatile
        private var INSTANCE : MoneyWarehouseDatabase? = null

        @Synchronized
        fun getInstance(context: Context): MoneyWarehouseDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            val instance = Room.databaseBuilder(context.applicationContext, MoneyWarehouseDatabase::class.java, "moneywarehouse")
                .build()
            INSTANCE = instance
            return instance
        }
    }
}
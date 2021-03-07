package com.example.narcosdb.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.narcosdb.dao.*
import com.example.narcosdb.entity.*

@Database(entities = [Drug::class, MoneyWarehouse::class, DrugWarehouse::class, Contact::class, DrugBuy::class, DrugSales::class, DrugInWarehouse::class], version = 1, exportSchema = false) //add as array if multiple
abstract class DrugDatabase : RoomDatabase() {
    abstract fun drugDao(): DrugDao
    abstract fun moneyWarehouseDao(): MoneyWarehouseDao
    abstract fun drugWarehouseDao(): DrugWarehouseDao
    abstract fun buyDao(): BuyDao
    abstract fun salesDao(): SalesDao
    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var INSTANCE : DrugDatabase? = null

        @Synchronized
        fun getInstance(context: Context): DrugDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

                val instance = Room.databaseBuilder(context.applicationContext, DrugDatabase::class.java, "drugdatabase")
                        .build()
            INSTANCE = instance
            return instance
        }
    }
}
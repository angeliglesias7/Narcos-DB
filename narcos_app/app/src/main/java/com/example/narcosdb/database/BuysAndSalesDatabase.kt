package com.example.narcosdb.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.narcosdb.dao.BuyDao
import com.example.narcosdb.dao.SalesDao
import com.example.narcosdb.entity.DrugBuy
import com.example.narcosdb.entity.DrugSales


@Database(entities = [DrugBuy::class, DrugSales::class], version = 1, exportSchema = false) //add as array if multiple
abstract class BuysAndSalesDatabase : RoomDatabase() {
    abstract fun buyDao(): BuyDao
    abstract fun salesDao(): SalesDao

    companion object {
        @Volatile
        private var INSTANCE : BuysAndSalesDatabase? = null

        @Synchronized
        fun getBuyInstance(context: Context): BuysAndSalesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            val instance = Room.databaseBuilder(context.applicationContext, BuysAndSalesDatabase::class.java, "drugbuy")
                .build()
            INSTANCE = instance
            return instance
        }

        @Synchronized
        fun getSalesInstance(context: Context): BuysAndSalesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            val instance = Room.databaseBuilder(context.applicationContext, BuysAndSalesDatabase::class.java, "drugsales")
                .build()
            INSTANCE = instance
            return instance
        }
    }
}

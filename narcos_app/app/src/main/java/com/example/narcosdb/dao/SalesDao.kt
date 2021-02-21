package com.example.narcosdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.narcosdb.entity.DrugSales


@Dao
interface SalesDao {

    @Query("SELECT * FROM drugsales")
    fun getSalesHistory(): LiveData<List<DrugSales>>
}

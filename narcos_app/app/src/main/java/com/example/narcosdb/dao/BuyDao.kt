package com.example.narcosdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.narcosdb.entity.DrugBuy

@Dao
interface BuyDao {

    @Query("SELECT * FROM drugbuy")
    fun getBuyHistory(): LiveData<List<DrugBuy>>
}
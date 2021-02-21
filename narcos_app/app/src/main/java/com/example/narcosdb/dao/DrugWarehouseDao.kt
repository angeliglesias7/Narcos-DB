package com.example.narcosdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.narcosdb.entity.DrugWarehouse

@Dao
interface DrugWarehouseDao {
    @Insert
    suspend fun insert(drugWarehouse: DrugWarehouse?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(drugWarehouse: DrugWarehouse?)

    @Delete
    suspend fun delete(drugWarehouse: DrugWarehouse?)

    //Destroy
    //Get drugs in warehouse

    @Query("SELECT * FROM drugwarehouse")
    fun getAll(): LiveData<List<DrugWarehouse>>
}
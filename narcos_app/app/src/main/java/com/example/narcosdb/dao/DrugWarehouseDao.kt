package com.example.narcosdb.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.narcosdb.entity.Drug
import com.example.narcosdb.entity.DrugInWarehouse
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.model.DrugInWarehousePOJO

@Dao
interface DrugWarehouseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(drugWarehouse: DrugWarehouse?): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(drugWarehouse: DrugWarehouse?): Int

    @Delete
    fun delete(drugWarehouse: DrugWarehouse?): Int

    //Destroy

    @Query("SELECT * FROM drugwarehouse")
    fun getAll(): LiveData<List<DrugWarehouse>>

    @Query("SELECT dea.drugName,dea.drugQuality,d.price,dea.amount, d.description from drugwarehouse as a, drug as d ,druginwarehouse as dea where  dea.drugName=d.name AND dea.drugQuality=d.quality AND dea.warehouseName= a.name AND a.name=:warehouseName")
    fun getDrugsInWarehouse(warehouseName: String): LiveData<List<DrugInWarehousePOJO>>

}
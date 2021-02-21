package com.example.narcosdb.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.DrugWarehouseDao
import com.example.narcosdb.entity.DrugWarehouse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DrugWarehouseRepo(
    private val drugWarehouseDao: DrugWarehouseDao
) {
    val allDrugWarehouses: LiveData<List<DrugWarehouse>> = drugWarehouseDao.getAll()

    @WorkerThread
    suspend fun insert(drugWarehouse: DrugWarehouse) = withContext(Dispatchers.IO) {
        try {
            drugWarehouseDao.insert(drugWarehouse)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    @WorkerThread
    suspend fun update(drugWarehouse: DrugWarehouse) = withContext(Dispatchers.IO) {
        try {
            drugWarehouseDao.update(drugWarehouse)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    @WorkerThread
    suspend fun delete(drugWarehouse: DrugWarehouse) = withContext(Dispatchers.IO) {
        try {
            drugWarehouseDao.delete(drugWarehouse)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }
}
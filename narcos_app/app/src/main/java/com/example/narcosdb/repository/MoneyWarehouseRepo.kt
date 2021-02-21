package com.example.narcosdb.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.MoneyWarehouseDao
import com.example.narcosdb.entity.MoneyWarehouse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MoneyWarehouseRepo(
    private val moneyWarehouseDao: MoneyWarehouseDao
) {
    val allWarehouses: LiveData<List<MoneyWarehouse>> = moneyWarehouseDao.getAllWarehouses()
    val allMoney: LiveData<Float> = moneyWarehouseDao.getAllMoney()
    val totalWarehouses: LiveData<Int> = moneyWarehouseDao.getTotalWarehouses()

    @WorkerThread
    suspend fun insert(moneyWarehouse: MoneyWarehouse) = withContext(Dispatchers.IO) {
        try {
            moneyWarehouseDao.insert(moneyWarehouse)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    @WorkerThread
    suspend fun update(moneyWarehouse: MoneyWarehouse) = withContext(Dispatchers.IO) {
        try {
            moneyWarehouseDao.update(moneyWarehouse)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    @WorkerThread
    suspend fun delete(moneyWarehouse: MoneyWarehouse) = withContext(Dispatchers.IO) {
        try {
            moneyWarehouseDao.delete(moneyWarehouse)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    @WorkerThread
    suspend fun transferMoney(origin: MoneyWarehouse, destination: MoneyWarehouse, amount: Int) = withContext(Dispatchers.IO) {
        try {
            moneyWarehouseDao.transferMoney(origin, destination, amount)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }
}
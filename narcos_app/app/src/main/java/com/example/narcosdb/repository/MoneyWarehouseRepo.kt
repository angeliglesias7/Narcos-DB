package com.example.narcosdb.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.MoneyWarehouseDao
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.entity.MoneyWarehouse
import kotlinx.coroutines.*

class MoneyWarehouseRepo(
    private val moneyWarehouseDao: MoneyWarehouseDao
) {
    private var insertValue: Long = 0
    private var updateValue: Int = 0
    private var deleteValue: Int = 0

    val allWarehouses: LiveData<List<MoneyWarehouse>> = moneyWarehouseDao.getAllWarehouses()
    val allMoney: LiveData<Float> = moneyWarehouseDao.getAllMoney()
    val totalWarehouses: LiveData<Int> = moneyWarehouseDao.getTotalWarehouses()

    suspend fun insert(moneyWarehouse: MoneyWarehouse) : Long{
        val insertCoroutine = GlobalScope.launch {
            val result: Deferred<Long> = async {
                moneyWarehouseDao.insert(moneyWarehouse)
            }
            insertValue = result.await()
        }
        joinAll(insertCoroutine)
        return insertValue
    }

    suspend fun update(moneyWarehouse: MoneyWarehouse) : Int {
        val updateCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                moneyWarehouseDao.update(moneyWarehouse)
            }
            updateValue = result.await()
        }
        joinAll(updateCoroutine)
        return updateValue
    }

    suspend fun delete(moneyWarehouse: MoneyWarehouse) : Int {
        val deleteCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                moneyWarehouseDao.delete(moneyWarehouse)
            }
            deleteValue = result.await()
        }
        joinAll(deleteCoroutine)
        return deleteValue
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
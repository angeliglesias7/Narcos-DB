package com.example.narcosdb.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.DrugWarehouseDao
import com.example.narcosdb.entity.Drug
import com.example.narcosdb.entity.DrugInWarehouse
import com.example.narcosdb.entity.DrugWarehouse
import com.example.narcosdb.model.DrugInWarehousePOJO
import kotlinx.coroutines.*
import java.lang.Exception

class DrugWarehouseRepo(
    private val drugWarehouseDao: DrugWarehouseDao
) {
    val allDrugWarehouses: LiveData<List<DrugWarehouse>> = drugWarehouseDao.getAll()
    private var insertValue: Long = 0
    private var updateValue: Int = 0
    private var deleteValue: Int = 0

    suspend fun insert(drugWarehouse: DrugWarehouse): Long {
        val insertCoroutine = GlobalScope.launch {
            val result: Deferred<Long> = async {
                drugWarehouseDao.insert(drugWarehouse)
            }
            insertValue = result.await()
        }
        joinAll(insertCoroutine)
        return insertValue
    }

    suspend fun update(drugWarehouse: DrugWarehouse): Int {
        val updateCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                drugWarehouseDao.update(drugWarehouse)
            }
            updateValue = result.await()
        }
        joinAll(updateCoroutine)
        return updateValue
    }


    suspend fun delete(drugWarehouse: DrugWarehouse): Int {
        val topLevelScope = CoroutineScope(SupervisorJob())
        val result: Deferred<Int> = topLevelScope.async {
            drugWarehouseDao.delete(drugWarehouse)
            throw RuntimeException("RuntimeException in async coroutine")
        }
        val deleteCoroutine = topLevelScope.launch {
            try {
                result.await()
            } catch (exception: Exception) {
                println("Handle $exception in try/catch")
            }
        }
        joinAll(deleteCoroutine)
        return deleteValue
    }

    /*suspend fun delete(drugWarehouse: DrugWarehouse): Int {
        val deleteCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                drugWarehouseDao.delete(drugWarehouse)
                throw Exception("No se puede borrar")
            }
            deleteValue = result.await()
        }
        joinAll(deleteCoroutine)
        return deleteValue
    }*/

    @WorkerThread
    fun getDrugsInWarehouse(warehouseName: String): LiveData<List<DrugInWarehousePOJO>> {
        return drugWarehouseDao.getDrugsInWarehouse(warehouseName)
    }
}
package com.example.narcosdb.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.DrugDao
import com.example.narcosdb.entity.Drug
import kotlinx.coroutines.*

class DrugRepo(
    private val drugDao: DrugDao
) {

    val allDrugs: LiveData<List<Drug>> = drugDao.getAll()
    private var insertValue: Long = 0
    private var updateValue: Int = 0
    private var deleteValue: Int = 0


    suspend fun insert(drug: Drug) : Long{
        val insertCoroutine = GlobalScope.launch {
            val result: Deferred<Long> = async {
                drugDao.insert(drug)
            }
            insertValue = result.await()
        }
        joinAll(insertCoroutine)
        return insertValue
    }

    suspend fun update(drug: Drug) : Int {
        val updateCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                drugDao.update(drug)
            }
            updateValue = result.await()
        }
        joinAll(updateCoroutine)
        return updateValue
    }

    suspend fun delete(drug: Drug) : Int {
        val deleteCoroutine = GlobalScope.launch {
            val result: Deferred<Int> = async {
                drugDao.delete(drug)
            }
            deleteValue = result.await()
        }
        joinAll(deleteCoroutine)
        return deleteValue
    }

}
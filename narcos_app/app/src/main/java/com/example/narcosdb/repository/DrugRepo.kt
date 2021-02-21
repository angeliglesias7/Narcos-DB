package com.example.narcosdb.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.narcosdb.dao.DrugDao
import com.example.narcosdb.entity.Drug
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DrugRepo(
    private val drugDao: DrugDao
) {

    val allDrugs: LiveData<List<Drug>> = drugDao.getAll()

    @WorkerThread
    suspend fun insert(drug: Drug) = withContext(Dispatchers.IO) {
        try {
            drugDao.insert(drug)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    @WorkerThread
    suspend fun update(drug: Drug) = withContext(Dispatchers.IO) {
        try {
            drugDao.update(drug)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

    @WorkerThread
    suspend fun delete(drug: Drug) = withContext(Dispatchers.IO) {
        try {
            drugDao.delete(drug)
        } catch (ex: Exception) {
            println(ex.message)
        }
    }

}
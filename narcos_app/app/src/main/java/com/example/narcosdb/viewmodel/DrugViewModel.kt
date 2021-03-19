package com.example.narcosdb.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.narcosdb.database.DrugDatabase
import com.example.narcosdb.entity.Drug
import com.example.narcosdb.repository.DrugRepo
import kotlinx.coroutines.*

class DrugViewModel(application: Application): AndroidViewModel(application) {

    private val repository: DrugRepo

    private val allDrugs: LiveData<List<Drug>>

    private var insertValue: Long = 0
    private var updateValue: Int = 0
    private var deleteValue: Int = 0

    init {
        val drugDao = DrugDatabase.getInstance(application).drugDao()
        repository = DrugRepo(drugDao)
        allDrugs = repository.allDrugs
    }

    suspend fun insert(drug: Drug) : Long {
        val insertCoroutine = GlobalScope.launch {
            val result = repository.insert(drug)
            insertValue = result
        }
        joinAll(insertCoroutine)
        return insertValue
    }

    suspend fun update(drug: Drug) : Int {
        val updateCoroutine = GlobalScope.launch {
            val result = repository.update(drug)
            updateValue = result
        }
        joinAll(updateCoroutine)
        return updateValue
    }

    suspend fun delete(drug: Drug) : Int {
        val deleteCoroutine = GlobalScope.launch {
            val result = repository.delete(drug)
            deleteValue = result
        }
        joinAll(deleteCoroutine)
        return deleteValue
    }

    fun getAll() = repository.allDrugs
}